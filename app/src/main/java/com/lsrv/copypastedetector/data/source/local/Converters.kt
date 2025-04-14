package com.lsrv.copypastedetector.data.source.local

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.room.TypeConverter
import com.lsrv.copypastedetector.data.entities.Snippet
import com.lsrv.copypastedetector.data.entities.Warning
import kotlinx.serialization.json.Json
import java.time.Instant

class Converters {
    @TypeConverter
    fun fromInstant(instant: Instant): Long {
        return instant.toEpochMilli()
    }
    @TypeConverter
    fun toInstant(epochMillis: Long): Instant {
        return Instant.ofEpochMilli(epochMillis)
    }
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromSnippetList(snippets: SnapshotStateList<Snippet>): String {
        return json.encodeToString(snippets.toList())
    }

    @TypeConverter
    fun toSnippetList(jsonString: String): SnapshotStateList<Snippet> {
        return mutableStateListOf<Snippet>().apply {
            addAll(json.decodeFromString(jsonString))
        }
    }

    @TypeConverter
    fun fromWarningList(warnings: SnapshotStateList<Warning>): String {
        return json.encodeToString(warnings.toList())
    }

    @TypeConverter
    fun toWarningList(jsonString: String): SnapshotStateList<Warning> {
        return mutableStateListOf<Warning>().apply {
            addAll(json.decodeFromString(jsonString))
        }
    }
}