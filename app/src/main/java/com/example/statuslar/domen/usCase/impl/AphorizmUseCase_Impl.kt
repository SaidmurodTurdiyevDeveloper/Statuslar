package com.example.statuslar.domen.usCase.impl

import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import com.example.statuslar.domen.repostory.AphorizmRepostory
import com.example.statuslar.domen.usCase.AphorizmUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AphorizmUseCase_Impl @Inject constructor(private var repostory: AphorizmRepostory) : AphorizmUseCase {

    override fun add(id: Long): Flow<Boolean> = flow {
        emit(repostory.insert(SpeachEnity(0, "Yangi varoq", id, 0)) >= 0)
    }

    override fun delete(data: SpeachEnity): Flow<Boolean> = flow {
        emit(repostory.delete(data) > 0)
    }

    override fun loadViews(id: Long): Flow<List<SpeachEnity>> = flow {
        repostory.getAll(id).collect {
            val list = ArrayList<SpeachEnity>(it)
            list.add(SpeachEnity(-1, "", id, 0))
            emit(list)
        }
    }

    override fun update(data: SpeachEnity): Flow<Boolean> = flow {
        if (data.favourite == 0) {
            data.favourite = 1
        } else
            data.favourite = 0
        emit(repostory.update(data) == Unit)
    }

    override suspend fun loadWiseMan(id: Long): Flow<WiseManEntity> = repostory.getWiseMan(id)
}