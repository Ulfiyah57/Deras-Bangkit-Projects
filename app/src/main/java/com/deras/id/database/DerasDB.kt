package com.deras.id.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class DerasDB: RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    companion object{
        @Volatile
        private var INSTANCE: DerasDB? = null
        @JvmStatic
        fun getDatabase(context: Context):DerasDB{
            if (INSTANCE == null){
                synchronized(DerasDB::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        DerasDB::class.java, "Deras_db")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as DerasDB
        }
    }
}