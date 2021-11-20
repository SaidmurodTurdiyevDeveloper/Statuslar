package com.example.statuslar.data.source.local.room.dao

import androidx.room.*
import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WiseManChangeDao {
    @Query("Select * From tablewisemen where id=:getId Limit 1")
    fun getDataWithId(getId: Long): Flow<WiseManEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: WiseManEntity):Long

    @Update
    suspend fun update(data: WiseManEntity)
}