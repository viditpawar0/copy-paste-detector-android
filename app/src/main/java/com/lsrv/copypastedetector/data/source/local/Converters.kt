package com.lsrv.copypastedetector.data.source.local

import androidx.room.TypeConverter
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
}