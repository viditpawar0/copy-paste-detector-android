package com.lsrv.copypastedetector.data.source.network

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.lsrv.copypastedetector.data.entities.Session
import com.lsrv.copypastedetector.data.source.network.serverresources.SessionResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import kotlinx.serialization.json.Json

class SessionNetworkDatasource(
    private val client: HttpClient
): NetworkDatasource<Session> {
    val sessions = mutableStateListOf<Session>()

    override suspend fun refresh() {
        sessions.clear()
        sessions.addAll(Json.decodeFromString<List<Session>>(client.get(SessionResource()).body()))
    }

    override suspend fun insert(t: Session) {
//        TODO("Not yet implemented")
    }

    override suspend fun update(t: Session) {
//        TODO("Not yet implemented")
    }

    override suspend fun delete(t: Session) {
//        TODO("Not yet implemented")
    }

    override suspend fun get(id: Int): Session {
        return Json.decodeFromString<Session>(client.get(SessionResource.Id(2)).body())
    }

    override fun getAll(): SnapshotStateList<Session> {
        return sessions
    }
}