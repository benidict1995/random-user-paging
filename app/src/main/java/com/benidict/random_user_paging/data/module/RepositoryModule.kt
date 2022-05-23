package com.benidict.random_user_paging.data.module

import androidx.paging.ExperimentalPagingApi
import com.benidict.random_user_paging.data.repository.UserRepositoryImpl
import com.benidict.random_user_paging.local.database.UserDB
import com.benidict.random_user_paging.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun providePassengerRepository(
        apiService: ApiService,
        userDB: UserDB
    ) = UserRepositoryImpl(apiService, userDB)
}