package com.example.statuslar.domen.usCase

import kotlinx.coroutines.flow.Flow

interface SplashUseCase {
    fun enterMenu(): Flow<Boolean>
}