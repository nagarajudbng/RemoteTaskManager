package com.pesto.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.pesto.core.data.source.local.entity.Task

@Dao
interface TaskDao {

    @Insert
    fun insert(task:Task):Long

    @Delete
    fun delete(task:Task)

    @Update
    fun update(task: Task)
}
