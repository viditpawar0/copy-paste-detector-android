package com.lsrv.copypastedetector.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalSnippet::class], version = 1, exportSchema = false)
abstract class LocalDB : RoomDatabase() {
    abstract fun snippetDao(): LocalSnippetDao
}