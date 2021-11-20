package com.example.statuslar.zZz_utills.extentions

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
