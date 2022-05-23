package com.benidict.random_user_paging.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey (
    @PrimaryKey val email: String,
    val prevKey: Int?,
    val nextKey: Int?
)