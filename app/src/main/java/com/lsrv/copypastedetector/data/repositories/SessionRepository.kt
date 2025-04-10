package com.lsrv.copypastedetector.data.repositories

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.lsrv.copypastedetector.data.entities.Session
import com.lsrv.copypastedetector.data.source.local.dao.LocalSnippetDao
import com.lsrv.copypastedetector.data.source.network.SessionNetworkDatasource

class SessionRepository(
    private val localSessionDao: LocalSnippetDao,
    private val sessionNetworkDatasource: SessionNetworkDatasource
): Repository<Session> {
    override suspend fun refresh() {
        sessionNetworkDatasource.refresh()
    }
    override suspend fun get(id: Int): Session {
        return sessionNetworkDatasource.get(id)
    }

    override suspend fun getAll(): SnapshotStateList<Session> {
        return sessionNetworkDatasource.getAll()
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun update(id: Int, item: Session) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(vararg items: Session) {
        TODO("Not yet implemented")
    }

    override suspend fun insert(item: Session) {
        TODO("Not yet implemented")
    }
}