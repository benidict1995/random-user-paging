package com.benidict.random_user_paging.ui.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import androidx.paging.map
import com.benidict.random_user_paging.data.repository.UserRepositoryImpl
import com.benidict.random_user_paging.domain.mapper.mapUserLocalToUserDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class UserListViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl
) : ViewModel() {

    val userState = userRepositoryImpl.loadUsers()
        .buffer()
        .cachedIn(viewModelScope)

    val userStateMediator =
        userRepositoryImpl
            .loadUsersFromMediator()
            .buffer()
            .map { pagingData ->
                pagingData.map { userLocal ->
                    userLocal.mapUserLocalToUserDomain()
                }
            }
            .cachedIn(viewModelScope)

}