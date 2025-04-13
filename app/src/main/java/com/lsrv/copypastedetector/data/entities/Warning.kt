package com.lsrv.copypastedetector.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lsrv.copypastedetector.domain.InstantSerializer
import kotlinx.serialization.Serializable
import java.time.Instant

@Entity(tableName = "warnings")
@Serializable
data class Warning(
    @PrimaryKey val id: Long?,
    val text: String,
    val clientName: String,
    val severity: Severity,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant
) {
    enum class Severity { LOW, MEDIUM, HIGH }
}
