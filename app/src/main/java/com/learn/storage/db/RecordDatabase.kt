package com.learn.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TheRecord::class], version = 1, exportSchema = false)
abstract class RecordDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao
}