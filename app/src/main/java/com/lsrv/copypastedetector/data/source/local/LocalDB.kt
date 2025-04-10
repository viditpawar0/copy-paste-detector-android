package com.lsrv.copypastedetector.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lsrv.copypastedetector.data.entities.Snippet
import com.lsrv.copypastedetector.data.source.local.dao.LocalSnippetDao

@Database(entities = [Snippet::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LocalDB : RoomDatabase() {
    abstract fun snippetDao(): LocalSnippetDao
}