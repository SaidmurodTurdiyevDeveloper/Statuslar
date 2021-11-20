package com.example.statuslar.ui.viewModel.impl

import androidx.lifecycle.*
import com.example.statuslar.data.model.Event
import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import com.example.statuslar.domen.usCase.AphorizmUseCase
import com.example.statuslar.ui.viewModel.AphorizmViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AphorizmViewModel_Impl @Inject constructor(private val usecase: AphorizmUseCase) :
    ViewModel(), AphorizmViewModel {

    private var wiseMenId = -1L

    private val _loadItems = MediatorLiveData<Event<List<SpeachEnity>>>()
    override val loadItems: LiveData<Event<List<SpeachEnity>>> get() = _loadItems

    private val _openItem = MutableLiveData<Event<Long>>()
    override val openItem: LiveData<Event<Long>> get() = _openItem

    private val _close = MutableLiveData<Event<Unit>>()
    override val close: LiveData<Event<Unit>> get() = _close

    private val _loadWiseMan = MutableLiveData<Event<WiseManEntity>>()
    override val loadWiseMan: LiveData<Event<WiseManEntity>> get() = _loadWiseMan

    private val _loadFirstItem = MutableLiveData<Event<Unit>>()
    override val loadFirstItem: LiveData<Event<Unit>> get() = _loadFirstItem

    private val _openClickedLiveData = MutableLiveData<Event<Unit>>()
    override val openClickedLiveData: LiveData<Event<Unit>> get() = _openClickedLiveData

    private val _closeClickedLiveData = MutableLiveData<Event<Unit>>()
    override val closeClickedLiveData: LiveData<Event<Unit>> get() = _closeClickedLiveData

    private val _showMassageLiveData = MutableLiveData<Event<String>>()
    override val showMassageLiveData: LiveData<Event<String>> get() = _showMassageLiveData

    override fun add() {
        _openClickedLiveData.value = Event(Unit)
        usecase.add(wiseMenId).onEach {
            loadViews(wiseMenId)
            delay(200)
            if (it)
                _loadFirstItem.postValue(Event(Unit))
            _closeClickedLiveData.postValue(Event(Unit))
        }.catch {
            _showMassageLiveData.postValue(Event("Yangi varoq yaratib bo`lmadi!"))
            _closeClickedLiveData.postValue(Event(Unit))
        }.launchIn(viewModelScope)

    }

    override fun delete(data: SpeachEnity) {
        _openClickedLiveData.value = Event(Unit)
        usecase.delete(data).onEach {
            if (it) {
                delay(200)
                loadViews(wiseMenId)
            }
            _closeClickedLiveData.postValue(Event(Unit))
        }
            .catch {
                _showMassageLiveData.postValue(Event("Buyruq bajarilmadi!"))
                _closeClickedLiveData.postValue(Event(Unit))
            }.launchIn(viewModelScope)

    }

    override fun back() {
        _close.value = Event(Unit)
    }

    override fun itemTouch(id: Long) {
        _openItem.value = Event(id)
    }

    override fun update(data: SpeachEnity) {
        usecase.update(data).onEach {
            if (it) {
                loadViews(wiseMenId)
            }
        }
            .catch {
                _showMassageLiveData.postValue(Event("Buyruq bajarilmadi!"))
            }.launchIn(viewModelScope)
    }

    override fun loadViews(id: Long) {
        viewModelScope.launch {
            if (wiseMenId < 0) {
                usecase.loadWiseMan(id).onEach { data ->
                    wiseMenId = id
                    _loadWiseMan.postValue(Event(data))
                }.catch {
                    _loadWiseMan.postValue(Event(WiseManEntity(-1, "Unknown", "<none>", "None", "", -1)))
                }.launchIn(viewModelScope)
            }
            usecase.loadViews(id).onEach { list ->
                _loadItems.postValue(Event(list))
            }.catch {
                _loadItems.postValue(Event(emptyList()))
            }.launchIn(viewModelScope)
        }
    }
}