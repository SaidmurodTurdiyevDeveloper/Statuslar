package com.example.statuslar.domen.usCase

import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    suspend fun loadItems():Flow<List<WiseManEntity>>
    fun delete(data:WiseManEntity):Flow<Boolean>
}