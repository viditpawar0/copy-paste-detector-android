package com.lsrv.copypastedetector.data.source.network

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.lsrv.copypastedetector.data.entities.Snippet
import com.lsrv.copypastedetector.data.source.network.serverresources.SnippetResource
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

class SnippetNetworkDatasource(
    private val client: HttpClient
): NetworkDatasource<Snippet> {
    private val snippets = mutableStateListOf<Snippet>()

    override suspend fun refresh() {
        snippets.clear()
        snippets.addAll(Json.decodeFromString<List<Snippet>>(client.get(SnippetResource()).body()))
    }

    override suspend fun insert(t: Snippet): Long {
        return client.post(SnippetResource()) {
            header("Content-Type", "application/json")
            setBody(t)
        }.let {
            if (it.status != HttpStatusCode.Created) throw ServerResponseException(it, it.bodyAsText())
            (it.body() as Snippet).id?:throw ServerResponseException(it, it.bodyAsText())
        }
    }

    override suspend fun update(t: Snippet) {
        client.put(SnippetResource.Id(t.id?: throw IllegalArgumentException("Id cannot be null"))) {
            header("Content-Type", "application/json")
            setBody(t)
        }
    }

    override suspend fun delete(t: Snippet) {
        client.delete(SnippetResource.Id(t.id?: throw IllegalArgumentException("Id cannot be null"))).let {
            if (it.status != HttpStatusCode.OK) throw ServerResponseException(it, it.bodyAsText())
        }
    }

    override suspend fun get(id: Long): Snippet {
        return client.get(SnippetResource.Id(id)).let {
            if (it.status != HttpStatusCode.OK) throw ServerResponseException(it, it.bodyAsText())
            it.body()
        }
    }

    override fun getAll(): SnapshotStateList<Snippet> {
        return snippets
    }
}