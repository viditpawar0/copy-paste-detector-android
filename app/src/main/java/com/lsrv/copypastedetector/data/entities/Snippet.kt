package com.lsrv.copypastedetector.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lsrv.copypastedetector.domain.InstantSerializer
import kotlinx.serialization.Serializable
import java.time.Instant

@Entity(tableName = "snippets")
@Serializable
data class Snippet(
    @PrimaryKey val id: Long,
    val content: String,
    val type: Type,
    val clientName: String,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant
) {
    enum class Type { COPIED, PASTED }
}