package com.example.statuslar.data.repostory

import com.example.statuslar.data.source.local.room.MyDatabase
import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import com.example.statuslar.domen.repostory.SpeachRepostory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SpeachRepostory_Impl @Inject constructor(
    database: MyDatabase
) : SpeachRepostory {
    private var dao = database.getSpeachDao()
    private var daoAphorizm = database.getAphorizm()
    override suspend fun getSpeach(id: Long): Flow<SpeachEnity> = dao.getSpeach(id)

    override suspend fun update(data: SpeachEnity) = dao.update(data)

    override suspend fun delete(data: SpeachEnity): Int = daoAphorizm.delete(data)
}