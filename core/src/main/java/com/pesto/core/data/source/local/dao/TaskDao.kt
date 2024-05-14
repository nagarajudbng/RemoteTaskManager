package com.pesto.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pesto.core.data.source.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskEntity):Long
    @Query("DELETE FROM TaskEntity WHERE id = :taskID")
    suspend fun delete(taskID:Long)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(task: TaskEntity)
    @Query("SELECT * FROM TaskEntity")
    fun getTaskList(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE title LIKE '%' || :searchQuery || '%'")
     fun search(searchQuery:String): Flow<List<TaskEntity>>

}
