package com.lsrv.copypastedetector.data.source.network.serverresources

import io.ktor.resources.Resource

@Resource("warning")
class WarningResource {
    @Resource("{id}") class Id(
        val id: Long,
        val parent: WarningResource = WarningResource()
    )
}
