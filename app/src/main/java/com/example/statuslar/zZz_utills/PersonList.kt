package com.example.statuslar.zZz_utills

import android.annotation.SuppressLint
import com.example.statuslar.R
import com.example.statuslar.data.model.Person


@SuppressLint("ResourceType")
class PersonList {
    private var list = ArrayList<Person>()

    init {
        list.add(Person(1, "Alisher Navoiy", R.drawable.alisher))
        list.add(Person(2, "Albert Einstein", R.drawable.albert))
        list.add(Person(3, "Brus Lee", R.drawable.brus_lee))
        list.add(Person(4, "Islom Karimov", R.drawable.islomjon))
        list.add(Person(5, "Muhammad Ali", R.drawable.muhammad_ali))
        list.add(Person(6, "Steve Jobs", R.drawable.stive_jobs))
        list.add(Person(7, "Rich Man", R.drawable.wise_man))
        list.add(Person(8, "Islom Karimov", R.drawable.karimov))
        list.add(Person(9, "Steve Jobs", R.drawable.stive_jobs_2))
    }

    fun getList(): List<Person> = list

    fun getWithId(id: Int): Person {
        var d = list[0]
        list.forEach { item ->
            if (item.id == id)
                d = item
        }
        return d
    }
}