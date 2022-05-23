package com.benidict.random_user_paging.network

import com.benidict.random_user_paging.network.model.BaseResponse
import com.benidict.random_user_paging.network.model.user.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(".")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("results") results: Int,
    ): BaseResponse<List<UserResponse>>
}