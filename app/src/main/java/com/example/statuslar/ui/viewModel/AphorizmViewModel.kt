package com.example.statuslar.ui.viewModel

import androidx.lifecycle.LiveData
import com.example.statuslar.data.model.Event
import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import com.example.statuslar.data.source.local.room.entity.WiseManEntity

interface AphorizmViewModel {
    val loadItems: LiveData<Event<List<SpeachEnity>>>
    val openItem: LiveData<Event<Long>>
    val close: LiveData<Event<Unit>>
    val loadWiseMan: LiveData<Event<WiseManEntity>>
    val loadFirstItem:LiveData<Event<Unit>>
    val openClickedLiveData:LiveData<Event<Unit>>
    val closeClickedLiveData:LiveData<Event<Unit>>
    val showMassageLiveData:LiveData<Event<String>>

    fun add()
    fun delete(data: SpeachEnity)
    fun back()
    fun itemTouch(id: Long)
    fun loadViews(id: Long)
    fun update(data: SpeachEnity)
}