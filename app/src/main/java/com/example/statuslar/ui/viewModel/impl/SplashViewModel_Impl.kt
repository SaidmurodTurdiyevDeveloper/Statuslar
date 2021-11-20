package com.example.statuslar.ui.viewModel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.statuslar.data.model.Event
import com.example.statuslar.domen.usCase.SplashUseCase
import com.example.statuslar.ui.viewModel.SplashViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashViewModel_Impl @Inject constructor(useCase: SplashUseCase) : SplashViewModel, ViewModel() {
    private val _showMassageLiveData = MutableLiveData<Event<String>>()
    override val showMassageLiveData: LiveData<Event<String>>
        get() = _showMassageLiveData

    private val _openMenuLiveData = MutableLiveData<Event<Unit>>()
    override val openMenuLiveData: LiveData<Event<Unit>>
        get() = _openMenuLiveData

    init {
        useCase.enterMenu().onEach {
            if (it) {
                _showMassageLiveData.postValue(Event("Aktivlashdi"))
            }
            _openMenuLiveData.postValue(Event(Unit))
        }
            .catch {
                _showMassageLiveData.postValue(Event("Xatolik yuz berdi!"))
            }.launchIn(viewModelScope)

    }
}