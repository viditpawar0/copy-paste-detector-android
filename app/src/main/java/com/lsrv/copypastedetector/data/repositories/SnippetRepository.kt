package com.lsrv.copypastedetector.data.repositories

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.lsrv.copypastedetector.data.entities.Snippet
import com.lsrv.copypastedetector.data.source.local.dao.LocalSnippetDao
import com.lsrv.copypastedetector.data.source.network.SnippetNetworkDatasource

class SnippetRepository(
    private val localSnippetDao: LocalSnippetDao,
    private val snippetNetworkDatasource: SnippetNetworkDatasource
): Repository<Snippet> {
    val snippets = mutableStateListOf<Snippet>()

    override suspend fun refresh() {
        snippetNetworkDatasource.refresh()
    }
    override suspend fun get(id: Long): Snippet {
        return snippetNetworkDatasource.get(id)
    }

    override suspend fun getAll(): SnapshotStateList<Snippet> {
        return snippetNetworkDatasource.getAll()
    }

    override suspend fun insert(t: Snippet): Long {
        return snippetNetworkDatasource.insert(t)
    }

    override suspend fun insertAll(vararg t: Snippet) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(t: Snippet) {
        snippetNetworkDatasource.delete(t)
    }

    override suspend fun update(t: Snippet) {
        snippetNetworkDatasource.update(t)
    }
}