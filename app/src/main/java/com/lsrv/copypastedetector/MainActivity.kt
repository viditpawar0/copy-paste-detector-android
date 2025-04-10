package com.lsrv.copypastedetector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.lsrv.copypastedetector.data.repositories.SessionRepository
import com.lsrv.copypastedetector.data.repositories.SnippetRepository
import com.lsrv.copypastedetector.data.source.network.SessionNetworkDatasource
import com.lsrv.copypastedetector.data.source.network.SnippetNetworkDatasource
import com.lsrv.copypastedetector.ui.screens.MainScreen
import com.lsrv.copypastedetector.ui.viewmodels.MainScreenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen(
                viewModel = viewModel(factory = viewModelFactory {
                    addInitializer(MainScreenViewModel::class) {
                        MainScreenViewModel(
                            SnippetRepository(
                                (application as CopyPasteDetectorApplication).db.snippetDao(),
                                SnippetNetworkDatasource((application as CopyPasteDetectorApplication).client)
                            ),
                            SessionRepository(
                                (application as CopyPasteDetectorApplication).db.snippetDao(),
                                SessionNetworkDatasource((application as CopyPasteDetectorApplication).client)
                            )
                        )
                    }
                })
            )
        }
    }
}