package com.example.statuslar.data.source.local.room.dao

import androidx.room.*
import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import kotlinx.coroutines.flow.Flow

@Dao
interface SplashDao : WiseManChangeDao {
    @Query("SELECT TableSpeach.* FROM TableSpeach LEFT OUTER JOIN tablewisemen ON TableSpeach.wiseManId = tablewisemen.id where tablewisemen.id is NULL")
    fun getEmptyWiseManIdSpeaches(): Flow<List<SpeachEnity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<SpeachEnity>)

    @Delete
    suspend fun deleteAll(list: List<SpeachEnity>):Int
}