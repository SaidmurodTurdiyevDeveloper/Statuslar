package com.example.statuslar.data.source.local.shared

import android.content.Context
import android.content.SharedPreferences

class LocalStoradge(context: Context) {
    private var preferences: SharedPreferences =
        context.getSharedPreferences("LocalStoradge", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = preferences.edit()

    companion object {
        private var database: LocalStoradge? = null

        fun getInstance(context: Context): LocalStoradge {
            if (database == null) {
                database = LocalStoradge(context)
            }
            return database!!
        }
    }

    fun setFirst(id: Int) {
        editor.putInt("First_Enter_Id", id)
        editor.apply()
    }

    fun getFirst(): Int = preferences.getInt("First_Enter_Id", 0)
}