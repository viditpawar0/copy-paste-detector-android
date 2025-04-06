package com.lsrv.copypastedetector.data.source.network

import androidx.lifecycle.LiveData

class SnippetNetworkDatasource: NetworkDatasource<NetworkSnippet> {
    override suspend fun insert(t: NetworkSnippet) {
        TODO("Not yet implemented")
    }

    override suspend fun update(t: NetworkSnippet) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(t: NetworkSnippet) {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: Int): NetworkSnippet {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): LiveData<NetworkSnippet> {
        TODO("Not yet implemented")
    }
}