package com.lsrv.copypastedetector.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lsrv.copypastedetector.data.entities.Session

@Dao
interface LocalSessionDao {
    @Insert suspend fun insert(session: Session)
    @Insert suspend fun insertAll(vararg sessions: Session)
    @Update suspend fun update(session: Session)
    @Delete suspend fun delete(session: Session)
    @Query("SELECT * FROM sessions") fun getSessions(): LiveData<List<Session>>
    @Query("SELECT * FROM sessions WHERE id = :id") suspend fun getSession(id: Int): Session
}
