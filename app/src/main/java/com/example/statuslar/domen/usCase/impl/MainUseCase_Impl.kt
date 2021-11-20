package com.example.statuslar.domen.usCase.impl

import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import com.example.statuslar.domen.repostory.MainRepostory
import com.example.statuslar.domen.usCase.MainUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MainUseCase_Impl @Inject constructor(private var repostory: MainRepostory) : MainUseCase {

    override suspend fun loadItems(): Flow<List<WiseManEntity>> = repostory.getAll()

    override fun delete(data: WiseManEntity): Flow<Boolean> = flow {
        emit(repostory.delete(data) > 0)
    }
}