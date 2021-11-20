package com.example.statuslar.domen.repostory

import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import kotlinx.coroutines.flow.Flow

interface WiseManRepostory {
    suspend fun getItem(id: Long): Flow<WiseManEntity>
    suspend fun add(data: WiseManEntity): Long
    suspend fun update(data: WiseManEntity)
}