package com.lsrv.copypastedetector.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LocalSnippetDao {
    @Insert suspend fun insert (snippet: LocalSnippet)
    @Insert suspend fun insertAll(vararg snippets: LocalSnippet)
    @Update suspend fun update(snippet: LocalSnippet)
    @Delete suspend fun delete(snippet: LocalSnippet)
    @Query("SELECT * FROM snippets") suspend fun getSnippets(): LiveData<LocalSnippet>
    @Query("SELECT * FROM snippets WHERE id = :id") suspend fun getSnippet(id: Int): LocalSnippet
}