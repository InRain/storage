package com.learn.storage.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class TheRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val value: String
)