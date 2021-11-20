package com.example.statuslar.domen.usCase

import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import kotlinx.coroutines.flow.Flow

interface AphorizmUseCase {
    fun add(id: Long): Flow<Boolean>
    fun delete(data: SpeachEnity): Flow<Boolean>
    fun loadViews(id: Long): Flow<List<SpeachEnity>>
    fun update(data: SpeachEnity): Flow<Boolean>
    suspend fun loadWiseMan(id: Long): Flow<WiseManEntity>
}