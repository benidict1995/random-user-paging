package com.benidict.random_user_paging.network.model.user

import com.benidict.random_user_paging.network.model.location.LocationResponse

data class UserResponse(
    val name: NameResponse = NameResponse(),
    val email: String = "",
    val phone: String = "",
    val location: LocationResponse = LocationResponse(),
    val picture: PictureResponse = PictureResponse()
)