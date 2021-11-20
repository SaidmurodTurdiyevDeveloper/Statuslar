package com.example.statuslar.ui.viewModel

import androidx.lifecycle.LiveData
import com.example.statuslar.data.model.Event

interface MenuViewModel {
    val openHikmatlarLiveData: LiveData<Event<Unit>>
    val openAppInfoLiveData: LiveData<Event<Unit>>
    val quitLiveData: LiveData<Event<Unit>>

    fun openHikmatlar()
    fun openAppInfo()
    fun quit()
}