package com.lsrv.copypastedetector.data.source.network

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.lsrv.copypastedetector.data.entities.Warning
import com.lsrv.copypastedetector.data.source.network.serverresources.WarningResource
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

class WarningNetworkDatasource(
    private val client: HttpClient
) : NetworkDatasource<Warning> {
    private val warnings = mutableStateListOf<Warning>()

    override suspend fun refresh() {
        warnings.clear()
        warnings.addAll(Json.decodeFromString<List<Warning>>(client.get(WarningResource()).body()))
    }

    override suspend fun insert(t: Warning): Long {
        return client.post(WarningResource()) {
            header("Content-Type", "application/json")
            setBody(t)
        }.let {
            if (it.status != HttpStatusCode.Created)
                throw ServerResponseException(it, it.bodyAsText())
            (it.body() as Warning).id ?: throw ServerResponseException(it, it.bodyAsText())
        }
    }

    override suspend fun update(t: Warning) {
        client.put(WarningResource.Id(t.id ?: throw IllegalArgumentException("Id cannot be null"))) {
            header("Content-Type", "application/json")
            setBody(t)
        }
    }

    override suspend fun delete(t: Warning) {
        client.delete(WarningResource.Id(t.id ?: throw IllegalArgumentException("Id cannot be null"))).let {
            if (it.status != HttpStatusCode.OK)
                throw ServerResponseException(it, it.bodyAsText())
        }
    }

    override suspend fun get(id: Long): Warning {
        return client.get(WarningResource.Id(id)).let {
            if (it.status != HttpStatusCode.OK)
                throw ServerResponseException(it, it.bodyAsText())
            it.body()
        }
    }

    override fun getAll(): SnapshotStateList<Warning> {
        return warnings
    }
}
