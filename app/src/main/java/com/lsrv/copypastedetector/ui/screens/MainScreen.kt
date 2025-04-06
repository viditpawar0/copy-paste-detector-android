package com.lsrv.copypastedetector.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.lsrv.copypastedetector.ui.theme.CopyPasteDetectorTheme
import com.lsrv.copypastedetector.ui.viewmodels.MainScreenViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel
) {
    CopyPasteDetectorTheme {
        val snippets by viewModel.snippets.collectAsState()
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding)
            ) {
                items(snippets.size) {

                }
            }
        }
    }
}