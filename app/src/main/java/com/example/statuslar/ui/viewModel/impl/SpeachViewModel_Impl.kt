package com.example.statuslar.ui.viewModel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.statuslar.data.model.Event
import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import com.example.statuslar.domen.usCase.SpeachUseCase
import com.example.statuslar.ui.viewModel.SpechViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpeachViewModel_Impl @Inject constructor(private var useCase: SpeachUseCase) :
    ViewModel(), SpechViewModel {

    private var currentData: SpeachEnity? = null

    private val _loadItemLiveData = MutableLiveData<Event<SpeachEnity>>()
    override val loadItemLiveData: LiveData<Event<SpeachEnity>>
        get() = _loadItemLiveData

    private val _backLivedata = MutableLiveData<Event<Unit>>()
    override val backLivedata: LiveData<Event<Unit>>
        get() = _backLivedata

    private val _openChooseMessageDialogLiveData = MutableLiveData<Event<Unit>>()
    override val openChooseMessageDialogLiveData: LiveData<Event<Unit>>
        get() = _openChooseMessageDialogLiveData

    private val _changeTextLiveData = MutableLiveData<Event<Boolean>>()
    override val changeTextLiveData: LiveData<Event<Boolean>>
        get() = _changeTextLiveData

    private val _changeFavouruteLiveData = MutableLiveData<Event<Boolean>>()
    override val changeFavouruteLiveData: LiveData<Event<Boolean>>
        get() = _changeFavouruteLiveData

    private val _showMassageLiveData = MutableLiveData<Event<String>>()
    override val showMassageLiveData: LiveData<Event<String>>
        get() = _showMassageLiveData

    override fun loadSpeach(id: Long) {
        viewModelScope.launch {
            useCase.loadSpeach(id).onEach {
                _loadItemLiveData.postValue(Event(it))
                if (currentData == null)
                    currentData = it
            }.catch {
                this.emit(SpeachEnity(-1, "None", 0, 0))
            }.launchIn(viewModelScope)
        }
    }

    override fun back() {
        _backLivedata.value = Event(Unit)
    }

    override fun check(text: String) {
        if (text == currentData?.text ?: "None") {
            back()
        } else {
            _openChooseMessageDialogLiveData.value = Event(Unit)
        }
    }

    override fun delete(id: Long) {
        viewModelScope.launch {
            useCase.delete(currentData ?: useCase.loadSpeach(id).first()).onEach {
                if (it) {
                    delay(1200)
                    back()
                } else
                    _showMassageLiveData.postValue(Event("O`chirishning iloji bo`lmadi"))
            }
                .catch {
                    _showMassageLiveData.postValue(Event("Xatolik yuz beridi!"))
                }.launchIn(viewModelScope)
        }
    }

    override fun save(data: SpeachEnity, id: Long) {
        viewModelScope.launch {
            useCase.save(currentData ?: useCase.loadSpeach(id).first(), data).onEach {
                if (it) {
                    currentData = data
                    _changeTextLiveData.postValue(Event(true))
                } else
                    _showMassageLiveData.postValue(Event("Xotiraga saqlashning iloji bo`lmadi"))
            }
                .catch {
                    _showMassageLiveData.postValue(Event("Xatolik yuz beridi!"))
                }.launchIn(viewModelScope)
        }
    }

    override fun changeData(string: String) {
        _changeTextLiveData.value = Event(currentData?.text.equals(string))
    }

    override fun favourute(id: Long) {
        viewModelScope.launch {
            useCase.favorute(currentData ?: useCase.loadSpeach(id).first()).onEach {
                _changeFavouruteLiveData.postValue(Event(it.favourite == 1))
            }.catch {
                _changeFavouruteLiveData.postValue(Event(false))
                _showMassageLiveData.postValue(Event("Xatolik yuz beridi!"))
            }
                .launchIn(viewModelScope)
        }
    }
}
