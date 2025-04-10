package com.lsrv.copypastedetector

import android.app.Application
import androidx.room.Room
import com.lsrv.copypastedetector.data.source.local.LocalDB
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources

class CopyPasteDetectorApplication : Application() {
    lateinit var db: LocalDB
    val client = HttpClient(CIO) {
        install(Resources)
        defaultRequest {
            url(BuildConfig.SERVER_BASE_URL)
        }
    }
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, LocalDB::class.java, "local.db").build()
    }
}