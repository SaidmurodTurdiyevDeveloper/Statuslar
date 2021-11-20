package com.example.statuslar.domen.usCase.impl

import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import com.example.statuslar.domen.repostory.WiseManRepostory
import com.example.statuslar.domen.usCase.WiseManUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WiseManUseCase_Impl @Inject constructor(private var repostory: WiseManRepostory) : WiseManUseCase {

    override suspend fun loadItem(id: Long): Flow<WiseManEntity> = repostory.getItem(id)

    override fun add(data: WiseManEntity): Flow<Boolean> = flow {
        emit(repostory.add(data)>0)
    }
    override suspend fun edit(data: WiseManEntity) = repostory.update(data)
}