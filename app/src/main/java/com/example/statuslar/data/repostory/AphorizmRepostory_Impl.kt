package com.example.statuslar.data.repostory

import com.example.statuslar.data.source.local.room.MyDatabase
import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import com.example.statuslar.domen.repostory.AphorizmRepostory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AphorizmRepostory_Impl @Inject constructor(
    database: MyDatabase,
) : AphorizmRepostory {

    private var dao = database.getAphorizm()
    private var speachdao=database.getSpeachDao()
    private var wiseManDao=database.getAddOrEditWiseMen()

    override suspend fun getAll(id: Long): Flow<List<SpeachEnity>> = dao.getSpeach(id)

    override suspend fun delete(data: SpeachEnity): Int = dao.delete(data)

    override suspend fun insert(data: SpeachEnity): Long = dao.insert(data)

    override suspend fun getWiseMan(id: Long):Flow<WiseManEntity> = wiseManDao.getDataWithId(id)

    override suspend fun update(data: SpeachEnity) = speachdao.update(data)


}