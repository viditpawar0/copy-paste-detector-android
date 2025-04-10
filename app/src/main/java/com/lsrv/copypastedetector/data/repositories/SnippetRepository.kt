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
    override suspend fun get(id: Int): Snippet {
        return snippetNetworkDatasource.get(id)
    }

    override suspend fun getAll(): SnapshotStateList<Snippet> {
        return snippetNetworkDatasource.getAll()
    }

    override suspend fun insert(item: Snippet) {
//        TODO("Not yet implemented")
    }

    override suspend fun insertAll(vararg items: Snippet) {
//        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int) {
//        TODO("Not yet implemented")
    }

    override suspend fun update(
        id: Int,
        item: Snippet
    ) {
//        TODO("Not yet implemented")
    }
}