package com.lsrv.copypastedetector.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lsrv.copypastedetector.data.entities.Warning

@Dao
interface LocalWarningDao {
    @Insert suspend fun insert(warning: Warning)
    @Insert suspend fun insertAll(vararg warnings: Warning)
    @Update suspend fun update(warning: Warning)
    @Delete suspend fun delete(warning: Warning)
    @Query("SELECT * FROM warnings") fun getWarnings(): LiveData<List<Warning>>
    @Query("SELECT * FROM warnings WHERE id = :id") suspend fun getWarning(id: Int): Warning
}
