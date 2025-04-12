package com.lsrv.copypastedetector

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.lsrv.copypastedetector.data.source.local.LocalDB
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.kotlinx.json.json

val Context.dataStore by preferencesDataStore(name = "sessionIds")
class CopyPasteDetectorApplication : Application() {
    lateinit var db: LocalDB
    val client = HttpClient(CIO) {
        install(Resources)
        install(ContentNegotiation){
            json()
        }
        defaultRequest {
            url(BuildConfig.SERVER_BASE_URL)
        }
    }
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, LocalDB::class.java, "local.db").build()
    }
}