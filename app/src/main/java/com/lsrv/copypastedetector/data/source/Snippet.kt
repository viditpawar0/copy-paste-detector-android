package com.lsrv.copypastedetector.data.source

data class Snippet(
    val content: String,
    val type: Type
) {
    enum class Type { COPIED, PASTED }
}