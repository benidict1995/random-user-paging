package com.benidict.random_user_paging.network.model.location

data class LocationResponse(
    val street: StreetResponse = StreetResponse(),
    val city: String = "",
    val state: String = "",
    val country: String = ""
)