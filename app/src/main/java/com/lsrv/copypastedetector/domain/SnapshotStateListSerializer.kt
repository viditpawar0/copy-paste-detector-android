package com.lsrv.copypastedetector.domain

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class SnapshotStateListSerializer<T>(dataSerializer: KSerializer<T>) : KSerializer<SnapshotStateList<T>> {

    private val listSerializer = ListSerializer(dataSerializer)

    override val descriptor = listSerializer.descriptor

    override fun serialize(encoder: Encoder, value: SnapshotStateList<T>) {
        encoder.encodeSerializableValue(listSerializer, value.toList())
    }

    override fun deserialize(decoder: Decoder): SnapshotStateList<T> {
        val list = decoder.decodeSerializableValue(listSerializer)
        return mutableStateListOf<T>().apply { addAll(list) }
    }
}