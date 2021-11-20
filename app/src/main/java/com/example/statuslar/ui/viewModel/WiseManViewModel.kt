package com.example.statuslar.ui.viewModel

import androidx.lifecycle.LiveData
import com.example.statuslar.data.model.Event
import com.example.statuslar.data.model.Person
import com.example.statuslar.data.source.local.room.entity.WiseManEntity

interface WiseManViewModel {
    val closeLiveData: LiveData<Event<Unit>>
    val loadDataLiveData: LiveData<Event<WiseManEntity>>
    val loadErrorLiveData: LiveData<Event<String>>
    val loadImagePathLiveData: LiveData<Event<String>>
    val loadImageResIdLiveData: LiveData<Event<Person>>
    val openPersonDialogLiveData: LiveData<Event<Unit>>

    fun close()
    fun loadItem(id: Long)
    fun addData(data: WiseManEntity)
    fun editData(data: WiseManEntity)
    fun setImagePath(path: String)
    fun setImageInt(data:Person)
    fun openPersonDialog()
}