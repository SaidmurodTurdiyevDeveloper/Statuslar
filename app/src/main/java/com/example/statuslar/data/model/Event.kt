package com.example.statuslar.data.model


open class Event<out T>(private val data: T) {

    private var condition = true

    fun getDataOnlyOneTime(): T? {
        return if (condition) {
            condition = false
            data
        } else null
    }

    fun getDataEveryTime(): T = data
}
