package com.lsrv.copypastedetector.data.source.network.serverresources

import io.ktor.resources.Resource

@Resource("snippet")
class SnippetResource {
    @Resource("{id}") class Id(
        val id: Long,
        val parent: SnippetResource = SnippetResource()
    )
}