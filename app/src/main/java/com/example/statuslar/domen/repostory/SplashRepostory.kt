package com.example.statuslar.domen.repostory

import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import kotlinx.coroutines.flow.Flow

interface SplashRepostory {
    suspend fun isFirst(): Flow<Boolean>
    suspend fun setFirst(cond:Boolean)
    suspend fun loadData()
    suspend fun getEmptyWiseIdSpeches():Flow<List<SpeachEnity>>
    suspend  fun deleteEmpty(list: List<SpeachEnity>):Int
}
