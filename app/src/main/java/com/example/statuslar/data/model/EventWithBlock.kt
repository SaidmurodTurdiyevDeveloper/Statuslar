package com.example.statuslar.data.model

import com.example.statuslar.zZz_utills.AnyListener

open class EventWithBlock<T, Input, Output>(data: T, private val block: AnyListener<Input, Output>) : Event<T>(data) {
    fun getBlock(): AnyListener<Input, Output> = block
}