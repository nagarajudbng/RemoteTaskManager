package com.pesto.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pesto.core.data.source.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity):Long

    @Query("SELECT * FROM UserEntity")
    fun getUser():UserEntity
    @Query("DELETE FROM UserEntity")
    suspend fun delete()


}
