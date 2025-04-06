package com.lsrv.copypastedetector.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lsrv.copypastedetector.data.source.Snippet
import com.lsrv.copypastedetector.data.source.SnippetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val snippetRepository: SnippetRepository
) : ViewModel() {
    lateinit var snippets: MutableStateFlow<MutableList<Snippet>>

    init {
        viewModelScope.launch {
            snippets = MutableStateFlow<MutableList<Snippet>>(snippetRepository.getSnippets())
        }
    }
}