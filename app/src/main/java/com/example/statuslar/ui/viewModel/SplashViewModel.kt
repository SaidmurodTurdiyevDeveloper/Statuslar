package com.example.statuslar.ui.viewModel

import androidx.lifecycle.LiveData
import com.example.statuslar.data.model.Event

interface SplashViewModel {
    val showMassageLiveData: LiveData<Event<String>>
    val openMenuLiveData:LiveData<Event<Unit>>
}