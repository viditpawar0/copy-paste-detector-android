package com.lsrv.copypastedetector.data.repositories

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.lsrv.copypastedetector.data.entities.Warning
import com.lsrv.copypastedetector.data.source.local.dao.LocalWarningDao
import com.lsrv.copypastedetector.data.source.network.WarningNetworkDatasource

class WarningRepository(
    private val localWarningDao: LocalWarningDao,
    private val warningNetworkDatasource: WarningNetworkDatasource
) : LiveRepository<Warning> {

    override suspend fun refresh() {
        warningNetworkDatasource.refresh()
    }

    override suspend fun get(id: Long): Warning {
        return warningNetworkDatasource.get(id)
    }

    override suspend fun getAll(): SnapshotStateList<Warning> {
        return warningNetworkDatasource.getAll()
    }

    override suspend fun insert(t: Warning): Long {
        return warningNetworkDatasource.insert(t)
    }

    override suspend fun insertAll(vararg t: Warning) {
        t.forEach { insert(it) }
    }

    override suspend fun delete(t: Warning) {
        warningNetworkDatasource.delete(t)
    }

    override suspend fun update(t: Warning) {
        warningNetworkDatasource.update(t)
    }

    override fun enableLiveUpdates() {
        warningNetworkDatasource.enableLiveUpdates()
    }

    override fun disableLiveUpdates() {
        warningNetworkDatasource.disableLiveUpdates()
    }
}
