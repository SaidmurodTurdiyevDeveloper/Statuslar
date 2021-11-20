package com.example.statuslar.data.source.local.room.dao

import androidx.room.*
import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WiseMenDao {

    @Query("Select * FROM tablewisemen")
    fun getAll(): Flow<List<WiseManEntity>>

    @Delete
    suspend fun delete(data: WiseManEntity): Int

}