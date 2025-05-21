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

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SnippetsScreen(
    viewModel: MainScreenViewModel,
    modifier: Modifier = Modifier,
) {
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
            items(viewModel.snippets.size) {
                val snippet = viewModel.snippets[it]
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
