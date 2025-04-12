package com.lsrv.copypastedetector.data.repositories

import androidx.compose.runtime.snapshots.SnapshotStateList

interface Repository<T> {
    suspend fun refresh()
    suspend fun get(id: Long): T
    suspend fun getAll(): SnapshotStateList<T>
    suspend fun insert(item: T): Long
    suspend fun insertAll(vararg items: T)
    suspend fun delete(id: Int)
    suspend fun update(id: Int, item: T)
}