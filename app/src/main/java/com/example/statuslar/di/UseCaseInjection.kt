package com.example.statuslar.di

import com.example.statuslar.domen.usCase.*
import com.example.statuslar.domen.usCase.impl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseInjection {
    @Binds
    fun getAphorizmUseCase(usecaseImpl: AphorizmUseCase_Impl): AphorizmUseCase

    @Binds
    fun getMainUseCase(usecaseImpl: MainUseCase_Impl): MainUseCase

    @Binds
    fun getSpeachUseCase(usecaseImpl: SpeachUsecase_Impl):SpeachUseCase

    @Binds
    fun getSplashUseCase(usecaseImpl: SplashUsecase_Impl):SplashUseCase

    @Binds
    fun getWiseManUsaCase(wisemanusecaseImpl: WiseManUseCase_Impl):WiseManUseCase
}