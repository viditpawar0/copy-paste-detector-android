package com.lsrv.copypastedetector.data.source.network

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData

interface NetworkDatasource<T> {
    suspend fun refresh()
    suspend fun insert(t: T): Long
    suspend fun update(t: T)
    suspend fun delete(t: T)
    suspend fun get(id: Long): T
    fun getAll(): SnapshotStateList<T>
}