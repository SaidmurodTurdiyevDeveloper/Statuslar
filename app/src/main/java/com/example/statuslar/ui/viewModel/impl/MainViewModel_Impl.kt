package com.example.statuslar.ui.viewModel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.statuslar.data.model.Event
import com.example.statuslar.data.model.EventWithBlock
import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import com.example.statuslar.domen.usCase.MainUseCase
import com.example.statuslar.ui.viewModel.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel_Impl @Inject constructor(private val useCase: MainUseCase) :
    ViewModel(), MainViewModel {

    private val _openAphorizmFragment = MutableLiveData<Event<Long>>()
    override val openAphorizmFragment: LiveData<Event<Long>>
        get() = _openAphorizmFragment

    private val _loadItemsLiveData = MutableLiveData<Event<List<WiseManEntity>>>()
    override val loadItemsLiveData: LiveData<Event<List<WiseManEntity>>>
        get() = _loadItemsLiveData

    private val _backLivedata = MutableLiveData<Event<Unit>>()
    override val backLivedata: LiveData<Event<Unit>>
        get() = _backLivedata

    private val _addButtonLiveData = MutableLiveData<Event<Unit>>()
    override val addButtonLiveData: LiveData<Event<Unit>>
        get() = _addButtonLiveData

    private val _editLivedata = MutableLiveData<Event<Long>>()
    override val editLivedata: LiveData<Event<Long>>
        get() = _editLivedata

    private val _deleteLiveData = MutableLiveData<EventWithBlock<WiseManEntity, WiseManEntity, Unit>>()
    override val deleteLiveData: LiveData<EventWithBlock<WiseManEntity, WiseManEntity, Unit>>
        get() = _deleteLiveData

    private val _showMassageLiveData = MutableLiveData<Event<String>>()
    override val showMassageLiveData: LiveData<Event<String>>
        get() = _showMassageLiveData

    override fun loadItems() {
        viewModelScope.launch {
            useCase.loadItems().onEach {
                _loadItemsLiveData.postValue(Event(it))
            }.catch {
                _loadItemsLiveData.postValue(Event(emptyList()))
            }.launchIn(viewModelScope)
        }
    }

    override fun openAphorizmFragment(id: Long) {
        _openAphorizmFragment.value = Event(id)
    }

    override fun back() {
        _backLivedata.value = Event(Unit)
    }

    override fun add() {
        _addButtonLiveData.value = Event(Unit)
    }

    override fun edit(id: Long) {
        _editLivedata.value = Event(id)
    }

    override fun delete(data: WiseManEntity) {
        _deleteLiveData.postValue(EventWithBlock(data) { wiseManEntity ->
            useCase.delete(wiseManEntity).onEach {
                if (it) {
                    loadItems()
                } else {
                    _showMassageLiveData.postValue(Event("Motivator o`chirilmadi"))
                }
            }.catch {
                _showMassageLiveData.postValue(Event("Xatolik yuz berdi!"))
            }.launchIn(viewModelScope)
        })
    }
}