package com.lsrv.copypastedetector.data.repositories

import androidx.compose.runtime.snapshots.SnapshotStateList

interface Repository<T> {
    suspend fun refresh()
    suspend fun get(id: Long): T
    suspend fun getAll(): SnapshotStateList<T>
    suspend fun insert(t: T): Long
    suspend fun insertAll(vararg t: T)
    suspend fun delete(t: T)
    suspend fun update(t: T)
}