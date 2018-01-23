package com.junsu.smstoss.persistence

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(Item::class), version = 1)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        private var instance: ItemDatabase? = null
        @Synchronized
        fun get(context: Context): ItemDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        ItemDatabase::class.java, "SmstossDatabase")
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                            }
                        }).build()
            }
            return instance!!
        }
    }
}
