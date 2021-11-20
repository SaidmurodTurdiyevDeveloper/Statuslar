package com.example.statuslar.ui.viewModel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.statuslar.data.model.Event
import com.example.statuslar.ui.viewModel.MenuViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel_Impl @Inject constructor() : ViewModel(), MenuViewModel {

    private val _openHikmatlarLiveData = MediatorLiveData<Event<Unit>>()
    override val openHikmatlarLiveData: LiveData<Event<Unit>>
        get() = _openHikmatlarLiveData

    private val _openAppInfoLiveData = MediatorLiveData<Event<Unit>>()
    override val openAppInfoLiveData: LiveData<Event<Unit>>
        get() = _openAppInfoLiveData

    private val _quitliveData = MediatorLiveData<Event<Unit>>()
    override val quitLiveData: LiveData<Event<Unit>>
        get() = _quitliveData

    override fun openHikmatlar() {
        _openHikmatlarLiveData.value = Event(Unit)
    }

    override fun openAppInfo() {
        _openAppInfoLiveData.value = Event(Unit)
    }

    override fun quit() {
        _quitliveData.value = Event(Unit)
    }
}