package com.benidict.random_user_paging.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserLocal(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "first")
    val first: String = "",
    @ColumnInfo(name = "last")
    val last: String = "",
    @ColumnInfo(name = "email")
    val email: String? = "",
    @ColumnInfo(name = "phone")
    val phone: String? = "",
    @ColumnInfo(name = "streetNumber")
    val streetNumber: String? = "",
    @ColumnInfo(name = "streetName")
    val streetName: String? = "",
    @ColumnInfo(name = "city")
    val city: String? = "",
    @ColumnInfo(name = "state")
    val state: String? = "",
    @ColumnInfo(name = "country")
    val country: String? = "",
    @ColumnInfo(name = "picture")
    val picture: String? = ""
)