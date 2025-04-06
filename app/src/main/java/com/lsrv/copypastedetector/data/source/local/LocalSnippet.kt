package com.lsrv.copypastedetector.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lsrv.copypastedetector.data.source.Snippet

@Entity(tableName = "snippets")
data class LocalSnippet(
    @PrimaryKey val id: Int,
    @ColumnInfo val snippet: Snippet
)
