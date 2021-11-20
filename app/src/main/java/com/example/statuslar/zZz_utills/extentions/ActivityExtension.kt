package com.example.statuslar.zZz_utills.extentions

import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.changeStatusBarColor(color: Int){
    window.statusBarColor=color
}

fun AppCompatActivity.changeNavigationBarColor(color: Int){
    window.navigationBarColor=color
}