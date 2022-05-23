package com.benidict.random_user_paging.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.benidict.random_user_paging.domain.model.User
import com.benidict.random_user_paging.local.database.UserDB
import com.benidict.random_user_paging.local.model.UserLocal
import com.benidict.random_user_paging.network.ApiService
import com.benidict.random_user_paging.network.source.UserPagingDataSource
import com.benidict.random_user_paging.network.source.UserRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDB: UserDB
) {
    companion object {
        const val PAGE_SIZE = 10
    }

    fun loadUsers(): Flow<PagingData<User>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE
        ),
        pagingSourceFactory = {
            UserPagingDataSource(
                apiService = apiService
            )
        }
    ).flow

    fun loadUsersFromMediator(): Flow<PagingData<UserLocal>> {
        val pagingSourceFactory = {
            userDB.userDao().getAllUser()
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = UserRemoteMediator(
                apiService,
                userDB
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}