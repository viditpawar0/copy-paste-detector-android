package com.lsrv.copypastedetector.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.lsrv.copypastedetector.data.entities.Snippet

@Dao
interface LocalSnippetDao {
    @Insert suspend fun insert (snippet: Snippet)
    @Insert suspend fun insertAll(vararg snippets: Snippet)
    @Update suspend fun update(snippet: Snippet)
    @Delete suspend fun delete(snippet: Snippet)
    @Query("SELECT * FROM snippets") fun getSnippets(): LiveData<Snippet>
    @Query("SELECT * FROM snippets WHERE id = :id") suspend fun getSnippet(id: Int): Snippet
}