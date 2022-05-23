package com.benidict.random_user_paging.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.benidict.random_user_paging.local.model.RemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE email = :email")
    suspend fun remoteKeysUserEmail(email: String): RemoteKey?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAll()

}