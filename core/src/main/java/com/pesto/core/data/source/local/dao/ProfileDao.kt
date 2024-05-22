package com.pesto.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pesto.core.data.source.local.entity.ProfileEntity

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: ProfileEntity):Long

    @Query("SELECT * FROM ProfileEntity where userName LIKE '%' || :user || '%'")
    suspend fun getProfile(user:String):ProfileEntity
    @Query("SELECT * FROM ProfileEntity")
    suspend fun getProfile():ProfileEntity
    @Query("DELETE FROM ProfileEntity")
    suspend fun delete()


}
