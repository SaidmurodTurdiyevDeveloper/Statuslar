package com.example.statuslar.ui.viewModel

import androidx.lifecycle.LiveData
import com.example.statuslar.data.model.Event
import com.example.statuslar.data.model.EventWithBlock
import com.example.statuslar.data.source.local.room.entity.WiseManEntity

interface MainViewModel {
    val loadItemsLiveData: LiveData<Event<List<WiseManEntity>>>
    val backLivedata: LiveData<Event<Unit>>
    val addButtonLiveData: LiveData<Event<Unit>>
    val editLivedata: LiveData<Event<Long>>
    val deleteLiveData: LiveData<EventWithBlock<WiseManEntity,WiseManEntity,Unit>>
    val openAphorizmFragment: LiveData<Event<Long>>
    val showMassageLiveData:LiveData<Event<String>>

    fun loadItems()
    fun back()
    fun add()
    fun edit(id: Long)
    fun delete(data: WiseManEntity)
    fun openAphorizmFragment(id: Long)
}