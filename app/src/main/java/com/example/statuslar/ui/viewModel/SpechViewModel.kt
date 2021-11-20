package com.example.statuslar.ui.viewModel

import androidx.lifecycle.LiveData
import com.example.statuslar.data.model.Event
import com.example.statuslar.data.source.local.room.entity.SpeachEnity

interface SpechViewModel {
    val loadItemLiveData: LiveData<Event<SpeachEnity>>
    val backLivedata: LiveData<Event<Unit>>
    val openChooseMessageDialogLiveData: LiveData<Event<Unit>>
    val changeTextLiveData: LiveData<Event<Boolean>>
    val changeFavouruteLiveData: LiveData<Event<Boolean>>
    val showMassageLiveData:LiveData<Event<String>>

    fun loadSpeach(id: Long)
    fun back()
    fun check(text:String)
    fun delete(id: Long)
    fun favourute(id: Long)
    fun save(data: SpeachEnity,id: Long)
    fun changeData(string: String)

}