package com.benidict.random_user_paging.network.model

data class BaseResponse<T>(
    val results: T
)