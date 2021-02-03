package com.learn.storage.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
abstract class RecordDao {

    @Query("SELECT * FROM records WHERE id=:id")
    abstract fun getById(id: Long): TheRecord

    @Query("DELETE FROM records")
    abstract fun deleteAll()

    @Insert
    abstract fun insert(record: TheRecord)

}