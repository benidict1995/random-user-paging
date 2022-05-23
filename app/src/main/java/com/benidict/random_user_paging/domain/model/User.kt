package com.benidict.random_user_paging.domain.model

data class User(
    val uid: Int = 0,
    val title: String = "",
    val first: String = "",
    val last: String = "",
    val email: String? = "",
    val phone: String? = "",
    val streetNumber: String? = "",
    val streetName: String? = "",
    val city: String? = "",
    val state: String? = "",
    val country: String? = "",
    val picture: String? = ""
)