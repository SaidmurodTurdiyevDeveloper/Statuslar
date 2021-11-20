package com.example.statuslar.domen.repostory

import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import kotlinx.coroutines.flow.Flow

interface AphorizmRepostory {
    suspend fun getAll(id: Long): Flow<List<SpeachEnity>>
    suspend fun delete(data: SpeachEnity): Int
    suspend fun insert(data: SpeachEnity): Long
    suspend fun getWiseMan(id: Long): Flow<WiseManEntity>
    suspend fun update(data: SpeachEnity)
}