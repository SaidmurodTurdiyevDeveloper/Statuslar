package com.example.statuslar.data.repostory

import com.example.statuslar.data.source.local.room.MyDatabase
import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import com.example.statuslar.domen.repostory.MainRepostory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepostory_Impl @Inject constructor(
    database: MyDatabase,
) : MainRepostory {
    private var dao = database.getWiseMenDao()

    override suspend fun getAll(): Flow<List<WiseManEntity>> = dao.getAll()

    override suspend fun delete(data: WiseManEntity): Int = dao.delete(data)

}