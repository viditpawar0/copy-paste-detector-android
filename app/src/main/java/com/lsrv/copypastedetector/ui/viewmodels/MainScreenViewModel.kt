package com.lsrv.copypastedetector.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lsrv.copypastedetector.data.entities.Session
import com.lsrv.copypastedetector.data.repositories.SessionRepository
import com.lsrv.copypastedetector.data.entities.Snippet
import com.lsrv.copypastedetector.data.repositories.SnippetRepository
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val snippetRepository: SnippetRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {
    lateinit var snippets: SnapshotStateList<Snippet>
    lateinit var sessions: SnapshotStateList<Session>
    var isRefreshing by mutableStateOf(false)
    var selectedSession by mutableIntStateOf(0)
    var newSessionDialogUiState by mutableStateOf(NewSessionDialogUiState("", false))
    var newSessionCreatedDialogUiState by mutableStateOf(NewSessionCreatedDialogUiState(-1, false))
    init {
        viewModelScope.launch {
            snippets = snippetRepository.getAll()
            sessions = sessionRepository.getAll()
        }
    }
    fun refreshSessions() {
        viewModelScope.launch {
            isRefreshing = true
            sessionRepository.refresh()
            snippets.clear()
            snippets.addAll(sessions[selectedSession].snippets)
            isRefreshing = false
        }
    }
    fun createSession(session: Session) {
        viewModelScope.launch {
            val newSessionId = sessionRepository.insert(session)
            sessionRepository.getAll().add(session.copy(id = newSessionId))
            newSessionDialogUiState = newSessionDialogUiState.copy(open = false)
            newSessionCreatedDialogUiState = newSessionCreatedDialogUiState.copy(
                newSessionId = newSessionId,
                open = true
            )
        }
    }
}

data class NewSessionDialogUiState(
    val sessionNameInput: String,
    val open: Boolean
)

data class NewSessionCreatedDialogUiState(
    val newSessionId: Long,
    val open: Boolean
)