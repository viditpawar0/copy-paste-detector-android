package com.lsrv.copypastedetector.data.repositories

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lsrv.copypastedetector.data.entities.Session
import com.lsrv.copypastedetector.data.source.local.dao.LocalSessionDao
import com.lsrv.copypastedetector.data.source.local.dao.LocalSnippetDao
import com.lsrv.copypastedetector.data.source.network.SessionNetworkDatasource
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sessionIds")
class SessionRepository(
    private val localSessionDao: LocalSessionDao,
    private val sessionNetworkDatasource: SessionNetworkDatasource,
    private val dataStore: DataStore<Preferences>
): Repository<Session> {
    override suspend fun refresh() {
        sessionNetworkDatasource.refresh()
    }
    override suspend fun get(id: Long): Session {
        return sessionNetworkDatasource.get(id)
    }

    override suspend fun getAll(): SnapshotStateList<Session> {
        return sessionNetworkDatasource.getAll()
    }

    fun getOwned(): SnapshotStateList<Session> {
        val result = mutableStateListOf<Session>()
        dataStore.data.map {
            it.asMap().values.map {
                result.add(sessionNetworkDatasource.get(it as Long))
            }
        }
        return result
        TODO("Incomplete")
    }

    override suspend fun delete(t: Session) {
        sessionNetworkDatasource.delete(t)
    }

    override suspend fun update(t: Session) {
        sessionNetworkDatasource.update(t)
    }

    override suspend fun insertAll(vararg t: Session) {
        TODO("Not yet implemented")
    }

    override suspend fun insert(t: Session) : Long {
        val result = sessionNetworkDatasource.insert(t)
        dataStore.edit {
            it[longPreferencesKey(t.id.toString())] = t.id?:-1
        }
        return result
    }
}