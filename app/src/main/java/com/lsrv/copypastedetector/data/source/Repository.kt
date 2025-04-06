package com.lsrv.copypastedetector.data.source

interface Repository<T> {
    suspend fun get(id: Int): T
    suspend fun getAll(): MutableList<T>
    suspend fun insert(item: T)
    suspend fun insertAll(vararg items: T)
    suspend fun delete(id: Int)
    suspend fun update(id: Int, item: T)
}