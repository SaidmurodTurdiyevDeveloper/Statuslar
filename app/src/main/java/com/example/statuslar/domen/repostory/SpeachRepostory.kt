package com.example.statuslar.domen.repostory

import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import kotlinx.coroutines.flow.Flow

interface SpeachRepostory {
    suspend fun getSpeach(id: Long): Flow<SpeachEnity>
    suspend fun update(data: SpeachEnity)
    suspend fun delete(data: SpeachEnity):Int
}