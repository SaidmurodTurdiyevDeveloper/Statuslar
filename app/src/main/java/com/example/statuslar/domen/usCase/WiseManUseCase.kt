package com.example.statuslar.domen.usCase

import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import kotlinx.coroutines.flow.Flow

interface WiseManUseCase {
    suspend fun loadItem(id:Long):Flow<WiseManEntity>
    fun add(data:WiseManEntity):Flow<Boolean>
    suspend fun edit(data: WiseManEntity)
}