package com.benidict.random_user_paging.domain.mapper

import com.benidict.random_user_paging.domain.model.User
import com.benidict.random_user_paging.local.model.UserLocal
import com.benidict.random_user_paging.network.model.user.UserResponse

fun UserLocal.mapUserLocalToUserDomain() =
    User(
        email = this.email,
        title = this.title,
        first = this.first,
        last = this.last,
        phone = this.phone,
        streetName = this.streetName,
        streetNumber = this.streetNumber,
        city = this.city,
        state = this.state,
        country = this.country,
        picture = this.picture
    )

fun List<UserResponse>.mapUserRemoteToLocal() = this.map {
    UserLocal(
        email = it.email,
        title = it.name.title,
        first = it.name.first,
        last = it.name.last,
        phone = it.phone,
        streetName = it.location.street.name,
        streetNumber = it.location.street.number,
        city = it.location.city,
        state = it.location.state,
        country = it.location.country,
        picture = it.picture.thumbnail
    )
}.toList()

fun List<UserResponse>.mapUserRemoteToDomain() = this.map {
    User(
        email = it.email,
        title = it.name.title,
        first = it.name.first,
        last = it.name.last,
        phone = it.phone,
        streetName = it.location.street.name,
        streetNumber = it.location.street.number,
        city = it.location.city,
        state = it.location.state,
        country = it.location.country,
        picture = it.picture.thumbnail
    )
}.toList()