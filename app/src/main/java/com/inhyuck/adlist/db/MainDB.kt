package com.inhyuck.adlist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.inhyuck.adlist.db.dao.AdDao
import com.inhyuck.adlist.db.entity.Ad

@Database(entities = [Ad::class], version = 1, exportSchema = false)
abstract class MainDB: RoomDatabase() {
    abstract fun adDao(): AdDao

    companion object {
        @Volatile private var INSTANCE: MainDB? = null

        fun getInstance(context: Context): MainDB = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                MainDB::class.java, "main.db").build()
    }
}