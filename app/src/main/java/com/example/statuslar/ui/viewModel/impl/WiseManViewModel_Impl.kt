package com.example.statuslar.ui.viewModel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.statuslar.data.model.Event
import com.example.statuslar.data.model.Person
import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import com.example.statuslar.domen.usCase.WiseManUseCase
import com.example.statuslar.ui.viewModel.WiseManViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WiseManViewModel_Impl @Inject constructor(private var useCase: WiseManUseCase) :
    ViewModel(), WiseManViewModel {
    private var wiseMan = WiseManEntity(-1, "None", "none", "none", "", -1)

    private val _closeLiveData = MutableLiveData<Event<Unit>>()
    override val closeLiveData: LiveData<Event<Unit>> get() = _closeLiveData

    private val _loadDataLiveData = MutableLiveData<Event<WiseManEntity>>()
    override val loadDataLiveData: LiveData<Event<WiseManEntity>> get() = _loadDataLiveData

    private val _loadErrorLiveData = MutableLiveData<Event<String>>()
    override val loadErrorLiveData: LiveData<Event<String>> get() = _loadErrorLiveData

    private val _loadImagePathLiveData = MutableLiveData<Event<String>>()
    override val loadImagePathLiveData: LiveData<Event<String>> get() = _loadImagePathLiveData

    private val _openPersonDialogLivedata = MutableLiveData<Event<Unit>>()
    override val openPersonDialogLiveData: LiveData<Event<Unit>> get() = _openPersonDialogLivedata

    private val _loadImageResIdLiveData = MutableLiveData<Event<Person>>()
    override val loadImageResIdLiveData: LiveData<Event<Person>> get() = _loadImageResIdLiveData

    override fun close() {
        _closeLiveData.postValue(Event(Unit))
    }

    override fun loadItem(id: Long) {
        viewModelScope.launch {
            if (id >= 0) {
                useCase.loadItem(id).onEach {
                    wiseMan = it
                    _loadDataLiveData.postValue(Event(it))
                }.catch {
                    emit(wiseMan)
                }.launchIn(viewModelScope)
            }
        }
    }

    override fun addData(data: WiseManEntity) {
        data.pathId = wiseMan.pathId
        data.imageId = wiseMan.imageId
        useCase.add(data).onEach {
            if (it) {
                _closeLiveData.postValue(Event(Unit))
            } else {
                _loadErrorLiveData.postValue(Event("Malumotlarni tekshirib ko`ring"))
            }
        }.catch {
            _loadErrorLiveData.postValue(Event("Xatolik yuz berdi!"))
        }.launchIn(viewModelScope)
    }

    override fun editData(data: WiseManEntity) {
        viewModelScope.launch {
            data.imageId = wiseMan.imageId
            data.pathId = wiseMan.pathId
            useCase.edit(data)
            _closeLiveData.postValue(Event(Unit))
        }
    }

    override fun openPersonDialog() {
        _openPersonDialogLivedata.value = Event(Unit)
    }

    override fun setImagePath(path: String) {
        wiseMan.pathId = path
        _loadImagePathLiveData.value = Event(path)
    }

    override fun setImageInt(data: Person) {
        wiseMan.imageId = data.id
        _loadImageResIdLiveData.value = Event(data)
    }
}