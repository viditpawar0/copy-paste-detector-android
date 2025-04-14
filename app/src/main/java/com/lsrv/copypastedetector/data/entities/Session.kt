package com.lsrv.copypastedetector.data.entities

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lsrv.copypastedetector.domain.InstantSerializer
import com.lsrv.copypastedetector.domain.SnapshotStateListSerializer
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
@Entity(tableName = "sessions")
data class Session(
    @PrimaryKey
    val id: Long? = null,
    val name: String,
    @Serializable(with = InstantSerializer::class)
    val endsAt: Instant,
    @Serializable(with = SnapshotStateListSerializer::class)
    val snippets: SnapshotStateList<Snippet>,
    @Serializable(with = SnapshotStateListSerializer::class)
    val warnings: SnapshotStateList<Warning>
)
