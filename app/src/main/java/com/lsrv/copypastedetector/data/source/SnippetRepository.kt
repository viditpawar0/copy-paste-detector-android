package com.lsrv.copypastedetector.data.source

import com.lsrv.copypastedetector.data.source.local.LocalSnippetDao
import com.lsrv.copypastedetector.data.source.network.SnippetNetworkDatasource

class SnippetRepository(
    private val localSnippetDao: LocalSnippetDao,
    private val snippetNetworkDatasource: SnippetNetworkDatasource
): Repository<Snippet> {
    override suspend fun get(id: Int): Snippet {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): MutableList<Snippet> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(item: Snippet) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(vararg items: Snippet) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun update(
        id: Int,
        item: Snippet
    ) {
        TODO("Not yet implemented")
    }
}