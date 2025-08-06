package com.lsrv.copypastedetector.data.repositories

interface LiveRepository<T>: Repository<T> {
    fun enableLiveUpdates()
    fun disableLiveUpdates()
}