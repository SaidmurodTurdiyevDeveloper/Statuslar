package com.example.statuslar.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import kotlinx.coroutines.flow.Flow

@Dao
interface SpeachDao {
    @Query("SELECT * FROM TableSpeach Where id=:id Limit 1")
    fun getSpeach(id: Long): Flow<SpeachEnity>

    @Update
    suspend fun update(data: SpeachEnity)
}