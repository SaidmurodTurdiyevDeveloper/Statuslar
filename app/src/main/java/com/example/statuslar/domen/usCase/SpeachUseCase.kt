package com.example.statuslar.domen.usCase

import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import kotlinx.coroutines.flow.Flow

interface SpeachUseCase {
    suspend fun loadSpeach(id:Long):Flow<SpeachEnity>
    fun save(oldData:SpeachEnity,newData:SpeachEnity):Flow<Boolean>
    fun delete(data:SpeachEnity):Flow<Boolean>
    fun favorute(data: SpeachEnity):Flow<SpeachEnity>
}