package com.example.statuslar.di

import android.content.Context
import com.example.statuslar.data.source.local.room.MyDatabase
import com.example.statuslar.data.source.local.shared.LocalStoradge
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseInjection {
    @Provides
    fun getDatabase(@ApplicationContext context: Context): MyDatabase = MyDatabase.init(context)

    @Provides
    fun getLocalStoradge(@ApplicationContext context: Context): LocalStoradge = LocalStoradge.getInstance(context)
}