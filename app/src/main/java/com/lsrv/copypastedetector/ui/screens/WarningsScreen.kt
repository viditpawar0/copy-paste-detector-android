package com.lsrv.copypastedetector.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lsrv.copypastedetector.ui.viewmodels.MainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarningsScreen(
    viewModel: MainScreenViewModel,
    modifier: Modifier = Modifier,
) {
    println(viewModel.warnings)
    PullToRefreshBox(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        isRefreshing = viewModel.isRefreshing,
        onRefresh = { viewModel.refreshSessions() }

    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.warnings.size) {
                val warning = viewModel.warnings[it]
                ListItem(
                    headlineContent = { Text(warning.clientName) },
                    supportingContent = { Text(warning.text) },
                    overlineContent = { Text(warning.severity.toString()) },
                )
                HorizontalDivider()
            }
        }
    }
}