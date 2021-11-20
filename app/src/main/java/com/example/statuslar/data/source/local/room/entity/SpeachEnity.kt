package com.example.statuslar.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TableSpeach")
data class SpeachEnity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var text: String,
    var wiseManId: Long,
    var favourite: Int
)
