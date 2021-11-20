package com.example.statuslar.data.source.local.room.dao

import androidx.room.*
import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import kotlinx.coroutines.flow.Flow

@Dao
interface AphorizmDao {
    @Query("Select * from TableSpeach where wiseManId=:idWiseMen")
    fun getSpeach(idWiseMen: Long): Flow<List<SpeachEnity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: SpeachEnity):Long

    @Delete
    suspend fun delete(data: SpeachEnity):Int
}