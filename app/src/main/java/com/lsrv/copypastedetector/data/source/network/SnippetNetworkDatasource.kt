package com.lsrv.copypastedetector.data.source.network

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.lsrv.copypastedetector.data.entities.Snippet
import com.lsrv.copypastedetector.data.source.network.serverresources.SnippetResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class SnippetNetworkDatasource(
    private val client: HttpClient
): NetworkDatasource<Snippet> {
    val snippets = mutableStateListOf<Snippet>()

    override suspend fun refresh() {
        snippets.clear()
        snippets.addAll(Json.decodeFromString<List<Snippet>>(client.get(SnippetResource()).body()))
    }

    override suspend fun insert(t: Snippet): Long {
        return (client.post(SnippetResource()) {
            setBody(t)
        }.body() as Snippet).id
    }

    override suspend fun update(t: Snippet) {
//        TODO("Not yet implemented")
    }

    override suspend fun delete(t: Snippet) {
//        TODO("Not yet implemented")
    }

    override suspend fun get(id: Long): Snippet {
        return Json.decodeFromString<Snippet>(client.get(SnippetResource.Id(2)).body())
    }

    override fun getAll(): SnapshotStateList<Snippet> {
        return snippets
    }
}