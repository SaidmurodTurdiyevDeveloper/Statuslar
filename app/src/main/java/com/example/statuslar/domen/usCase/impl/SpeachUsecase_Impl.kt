package com.example.statuslar.domen.usCase.impl

import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import com.example.statuslar.domen.repostory.SpeachRepostory
import com.example.statuslar.domen.usCase.SpeachUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SpeachUsecase_Impl @Inject constructor(private var repostory: SpeachRepostory) : SpeachUseCase {

    override suspend fun loadSpeach(id: Long): Flow<SpeachEnity> = repostory.getSpeach(id)

    override fun save(oldData: SpeachEnity, newData: SpeachEnity): Flow<Boolean> = flow {
        if (oldData.text != newData.text) {
            newData.id = oldData.id
            newData.wiseManId = oldData.wiseManId
            newData.favourite = oldData.favourite
            repostory.update(newData)
        }
        emit(true)
    }

    override fun delete(data: SpeachEnity): Flow<Boolean> = flow {
        emit(repostory.delete(data) > 0)
    }

    override fun favorute(data: SpeachEnity): Flow<SpeachEnity> = flow {
        data.favourite = if (data.favourite == 0) 1 else 0
        repostory.update(data)
        delay(300)
        emit(repostory.getSpeach(data.id).first())
    }
}