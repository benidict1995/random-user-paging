package com.benidict.random_user_paging.ui.ext

import com.benidict.random_user_paging.domain.model.User

fun User.fullName() = "${this.first} ${this.last}"

fun User.address() = "${this.streetName}, ${this.city} ${this.state}, ${this.country}"