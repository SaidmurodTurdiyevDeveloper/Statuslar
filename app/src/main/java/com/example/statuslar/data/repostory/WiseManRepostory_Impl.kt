package com.example.statuslar.data.repostory

import com.example.statuslar.data.source.local.room.MyDatabase
import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import com.example.statuslar.domen.repostory.WiseManRepostory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WiseManRepostory_Impl @Inject constructor(
     database: MyDatabase,
) : WiseManRepostory {
    private var dao=database.getAddOrEditWiseMen()
    override suspend fun getItem(id: Long): Flow<WiseManEntity> = dao.getDataWithId(id)

    override suspend fun add(data: WiseManEntity): Long = dao.insert(data)

    override suspend fun update(data: WiseManEntity) = dao.update(data)

}