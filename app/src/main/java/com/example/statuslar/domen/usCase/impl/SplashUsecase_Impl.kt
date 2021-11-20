package com.example.statuslar.domen.usCase.impl

import com.example.statuslar.domen.repostory.SplashRepostory
import com.example.statuslar.domen.usCase.SplashUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SplashUsecase_Impl @Inject constructor(private var repostory: SplashRepostory) : SplashUseCase {

    override fun enterMenu(): Flow<Boolean> = flow {
        repostory.isFirst().collect { event ->
            if (event) {
                repostory.getEmptyWiseIdSpeches().collect {
                    delay(1000)
                    emit(repostory.deleteEmpty(it) > 0)
                }
            } else {
                repostory.setFirst(true)
                repostory.loadData()
                delay(1000)
                emit(true)
            }
        }
    }
}