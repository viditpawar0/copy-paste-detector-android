package com.lsrv.copypastedetector.data.source.network

interface LiveNetworkDatasource<T>: NetworkDatasource<T> {
    fun enableLiveUpdates()
    fun disableLiveUpdates()
}