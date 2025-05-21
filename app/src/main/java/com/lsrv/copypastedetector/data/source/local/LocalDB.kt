package com.lsrv.copypastedetector.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lsrv.copypastedetector.data.entities.Session
import com.lsrv.copypastedetector.data.entities.Snippet
import com.lsrv.copypastedetector.data.entities.Warning
import com.lsrv.copypastedetector.data.source.local.dao.LocalSessionDao
import com.lsrv.copypastedetector.data.source.local.dao.LocalSnippetDao
import com.lsrv.copypastedetector.data.source.local.dao.LocalWarningDao

@Database(
    entities = [Snippet::class, Session::class, Warning::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class LocalDB : RoomDatabase() {
    abstract fun snippetDao(): LocalSnippetDao
    abstract fun warningDao(): LocalWarningDao
    abstract fun sessionDao(): LocalSessionDao
}