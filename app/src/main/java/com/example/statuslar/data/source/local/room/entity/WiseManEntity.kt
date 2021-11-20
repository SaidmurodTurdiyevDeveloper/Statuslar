package com.example.statuslar.data.source.local.room.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tablewisemen")
data class WiseManEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var name: String,
    var hashtag: String,
    var info: String,
    var pathId:String,
    var imageId: Int
)
