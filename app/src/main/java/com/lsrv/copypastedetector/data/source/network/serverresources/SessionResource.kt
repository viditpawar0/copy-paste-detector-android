package com.lsrv.copypastedetector.data.source.network.serverresources

import io.ktor.resources.Resource

@Resource("/session")
class SessionResource {
    @Resource("{id}") class Id(
        val id: Long,
        val parent: SessionResource = SessionResource()
    )
}