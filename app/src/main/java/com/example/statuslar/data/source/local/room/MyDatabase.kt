package com.example.statuslar.data.source.local.room

import android.content.Context
import androidx.room.*
import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import com.example.statuslar.data.source.local.room.dao.*
import com.example.statuslar.data.source.local.room.dao.WiseManChangeDao
import com.example.statuslar.data.source.local.room.dao.SpeachDao

@Database(
    entities = [WiseManEntity::class, SpeachEnity::class],
    version = 1
)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getAddOrEditWiseMen(): WiseManChangeDao
    abstract fun getWiseMenDao(): WiseMenDao
    abstract fun getSpeachDao(): SpeachDao
    abstract fun getAphorizm(): AphorizmDao
    abstract fun getSplashdao():SplashDao
    companion object {
        @Volatile
        private lateinit var INSTANCE: MyDatabase

        fun init(context: Context): MyDatabase {
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "new_database_hikmatlar"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}