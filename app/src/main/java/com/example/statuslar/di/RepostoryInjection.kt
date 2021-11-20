package com.example.statuslar.di

import com.example.statuslar.data.repostory.*
import com.example.statuslar.domen.repostory.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepostoryInjection {
    @Binds
    fun getRepostoryAphorithm(repostoryImpl: AphorizmRepostory_Impl): AphorizmRepostory

    @Binds
    fun getRepostoryMain(repostoryImpl: MainRepostory_Impl): MainRepostory

    @Binds
    fun getSpechRepostory(repostoryImpl: SpeachRepostory_Impl): SpeachRepostory

    @Binds
    fun getSplashRepostory(repostoryImpl: SplashRepostory_Impl): SplashRepostory

    @Binds
    fun getWismanRepostory(repostoryImpl: WiseManRepostory_Impl): WiseManRepostory
}