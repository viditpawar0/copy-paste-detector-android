package com.lsrv.copypastedetector.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lsrv.copypastedetector.data.entities.Session
import com.lsrv.copypastedetector.ui.theme.CopyPasteDetectorTheme
import com.lsrv.copypastedetector.ui.viewmodels.MainScreenViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel
) {
    CopyPasteDetectorTheme {
        val snippets = viewModel.snippets
        val sessions = viewModel.sessions
        val scope = rememberCoroutineScope()
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val topLevelRoutes = listOf(
            TopLevelRoute("Snippets", SnippetsDestination, Icons.Default.Menu),
            TopLevelRoute("Warnings", WarningsDestination, Icons.Default.Warning)
        )
        val navController = rememberNavController()
        ModalNavigationDrawer(
            drawerState = drawerState,
            modifier = Modifier.fillMaxSize(),
            drawerContent = {
                PullToRefreshBox(
                    modifier = Modifier.fillMaxSize(),
                    isRefreshing = viewModel.isRefreshing,
                    onRefresh = { viewModel.refreshSessions() }
                ) {
                    ModalDrawerSheet {
                        Text(
                            "Sessions",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                        HorizontalDivider()
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            items(sessions.size) {
                                val session = sessions[it]
                                NavigationDrawerItem(
                                    label = { Text(session.name) },
                                    onClick = {
                                        snippets.clear()
                                        snippets.addAll(session.snippets)
                                        viewModel.selectedSession = it
                                        scope.launch { drawerState.close() }
                                    },
                                    selected = viewModel.selectedSession == it
                                )
                            }
                        }
                    }
                }
            }
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(if (viewModel.sessions.size > 0) "${viewModel.sessions[viewModel.selectedSession].id}:${viewModel.sessions[viewModel.selectedSession].name}" else "Copy Paste Detector")
                        },
                        navigationIcon = {
                            IconButton({scope.launch { drawerState.open() }}) {
                                Icon(Icons.Default.Menu, "Sessions")
                            }
                        }
                    )
                },
                bottomBar = {
                    NavigationBar {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        topLevelRoutes.forEach{route ->
                            NavigationBarItem(
                                icon = { Icon(route.icon, route.name) },
                                label = { Text(route.name) },
                                selected = currentDestination?.hierarchy?.any{it.hasRoute(route.route::class)}?:false,
                                onClick = {
                                    navController.navigate(route.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            viewModel.newSessionDialogUiState = viewModel.newSessionDialogUiState.copy(open = true)
                        }
                    ) { Icon(Icons.Default.Add, "Create Session") }
                }
            ) { innerPadding ->
                NavHost(navController, SnippetsDestination, Modifier.padding(innerPadding)) {
                    composable<SnippetsDestination> { SnippetsScreen(viewModel) }
                    composable<WarningsDestination> { WarningsScreen(viewModel)  }
                }
            }
        }
        when {viewModel.newSessionDialogUiState.open ->
            Dialog(onDismissRequest = { viewModel.newSessionDialogUiState = viewModel.newSessionDialogUiState.copy(open = false) }) {
                val timePickerState = rememberTimePickerState()
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            viewModel.newSessionDialogUiState.sessionNameInput,
                            {
                                viewModel.newSessionDialogUiState =
                                    viewModel.newSessionDialogUiState.copy(sessionNameInput = it)
                            },
                            label = { Text("Session Name") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(KeyboardCapitalization.Words),
                        )
                        Text(
                            "Session Ends At:",
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.labelMedium
                        )
                        TimePicker(timePickerState)
                        Button(
                            {
                                viewModel.createSession(
                                    Session(
                                        name = viewModel.newSessionDialogUiState.sessionNameInput,
                                        endsAt = LocalDateTime.of(
                                            LocalDate.now(),
                                            LocalTime.of(
                                                timePickerState.hour,
                                                timePickerState.minute,
                                            ),
                                        ).atZone(ZoneId.systemDefault()).toInstant(),
                                        snippets = mutableStateListOf(),
                                        warnings = mutableStateListOf()
                                    ),
                                )
                            },
                        ) { Text("Create") }
                    }
                }
            }
        }
        when{viewModel.newSessionCreatedDialogUiState.open ->
            BasicAlertDialog({viewModel.newSessionCreatedDialogUiState = viewModel.newSessionCreatedDialogUiState.copy(open = false)}) {
                Card {
                    Column(
                        modifier =
                            Modifier.padding(32.dp)
                            .aspectRatio(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "New session created with id:",
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            viewModel.newSessionCreatedDialogUiState.newSessionId.toString(),
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}


data class TopLevelRoute<T: Any>(
    val name: String,
    val route: T,
    val icon: ImageVector,
)

@Serializable
object SnippetsDestination

@Serializable
object WarningsDestination