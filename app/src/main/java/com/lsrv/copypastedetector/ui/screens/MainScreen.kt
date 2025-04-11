package com.lsrv.copypastedetector.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.lsrv.copypastedetector.ui.theme.CopyPasteDetectorTheme
import com.lsrv.copypastedetector.ui.viewmodels.MainScreenViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
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
                            style = MaterialTheme.typography.headlineLarge,
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
                            if (viewModel.sessions.size > 0) {
                                Text(viewModel.sessions[viewModel.selectedSession].name)
                            } else {
                                Text("Snippets")
                            }
                        }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            viewModel.newSessionDialogOpen = true
                        }
                    ) { Icon(Icons.Default.Add, "Create Session") }
                }
            ) { innerPadding ->
                PullToRefreshBox(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    isRefreshing = viewModel.isRefreshing,
                    onRefresh = {viewModel.refreshSessions()}

                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(snippets.size) {
                            val snippet = snippets[it]
                            ListItem(
                                headlineContent = { Text(snippet.clientName) },
                                supportingContent = { Text(snippet.content) },
                                overlineContent = { Text(snippet.type.toString()) },
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
        when {viewModel.newSessionDialogOpen ->
            Dialog(onDismissRequest = { viewModel.newSessionDialogOpen = false }) {
                val datePickerState = rememberDatePickerState()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        viewModel.newSessionDialogUiState.sessionNameInput,
                        {
                            viewModel.newSessionDialogUiState =
                                viewModel.newSessionDialogUiState.copy(sessionNameInput = it)
                        },
                        label = { Text("Session Name") }
                    )
                    DatePicker(
                        datePickerState
                    )
                }
            }
        }
    }
}