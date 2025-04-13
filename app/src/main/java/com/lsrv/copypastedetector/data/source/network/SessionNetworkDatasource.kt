package com.lsrv.copypastedetector.data.source.network

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.lsrv.copypastedetector.data.entities.Session
import com.lsrv.copypastedetector.data.source.network.serverresources.SessionResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.resources.delete
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.plugins.resources.put
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json

class SessionNetworkDatasource(
    private val client: HttpClient
): NetworkDatasource<Session> {
    private val sessions = mutableStateListOf<Session>()

    override suspend fun refresh() {
        sessions.clear()
        sessions.addAll(Json.decodeFromString<List<Session>>(client.get(SessionResource()).body()))
    }

    override suspend fun insert(t: Session) : Long {
        return client.post(SessionResource()) {
            header("Content-Type", "application/json")
            setBody(t)
        }.let {
            if (it.status != HttpStatusCode.Created) throw ServerResponseException(it, it.bodyAsText())
            (it.body() as Session).id?:throw ServerResponseException(it, it.bodyAsText())
        }
    }

    override suspend fun update(t: Session) {
        client.put(SessionResource.Id(t.id?:throw IllegalArgumentException("Id cannot be null"))) {
            header("Content-Type", "application/json")
            setBody(t)
        }.let {
            if(it.status != HttpStatusCode.OK)
                throw ServerResponseException(it, it.bodyAsText())
        }
    }

    override suspend fun delete(t: Session) {
        client.delete(SessionResource.Id(t.id?:throw IllegalArgumentException("Id cannot be null")))
            .let {
                if(it.status != HttpStatusCode.OK)
                    throw ServerResponseException(it, it.bodyAsText())
            }
    }

    override suspend fun get(id: Long): Session {
        return client.get(SessionResource.Id(id)).let {
            if(it.status != HttpStatusCode.OK)
                throw ServerResponseException(it, it.bodyAsText())
            it.body()
        }
    }

    override fun getAll(): SnapshotStateList<Session> {
        return sessions
    }
}