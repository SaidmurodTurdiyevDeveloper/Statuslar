package com.example.statuslar.zZz_utills.extentions

import com.example.statuslar.data.model.Event

fun <T> loadObserverOnlyOneTime(data: Event<T>, block: (T) -> Unit) {
    val d = data.getDataOnlyOneTime()
    if (d != null) {
        block.invoke(d)
    }
}