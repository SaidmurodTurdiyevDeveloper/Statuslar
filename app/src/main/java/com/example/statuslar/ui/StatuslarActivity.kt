package com.example.statuslar.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.statuslar.R
import com.example.statuslar.zZz_utills.extentions.changeNavigationBarColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatuslarActivity : AppCompatActivity(R.layout.activity_statuslar) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeNavigationBarColor(Color.parseColor("#A6161100"))
    }
}