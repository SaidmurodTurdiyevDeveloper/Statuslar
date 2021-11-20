package com.example.statuslar.domen.repostory

import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import kotlinx.coroutines.flow.Flow

interface MainRepostory {
    suspend fun getAll(): Flow<List<WiseManEntity>>
    suspend fun delete(data: WiseManEntity):Int
}