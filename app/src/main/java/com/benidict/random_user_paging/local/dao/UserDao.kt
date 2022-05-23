package com.benidict.random_user_paging.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.benidict.random_user_paging.local.model.UserLocal

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(passengers: List<UserLocal>)

    @Query("SELECT * FROM user")
    fun getAllUser(): PagingSource<Int, UserLocal>

    @Query("DELETE FROM user")
    suspend fun deleteAllUser()
}