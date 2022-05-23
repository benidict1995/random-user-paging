package com.benidict.random_user_paging.network.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.benidict.random_user_paging.domain.mapper.mapUserRemoteToLocal
import com.benidict.random_user_paging.local.database.UserDB
import com.benidict.random_user_paging.local.model.RemoteKey
import com.benidict.random_user_paging.local.model.UserLocal
import com.benidict.random_user_paging.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class UserRemoteMediator (
    private val apiService: ApiService,
    private val userDB: UserDB
) : RemoteMediator<Int, UserLocal>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserLocal>
    ): MediatorResult {
        val page = when (val pagedKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> return pagedKeyData
            else -> pagedKeyData as Int
        }

        return try {
            withContext(Dispatchers.IO) {
                val response = apiService.getUsers(
                    page = page,
                    results = state.config.pageSize
                )
                val isEndOfList = response.results.isEmpty()
                userDB.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        userDB.apply {
                            userDao().deleteAllUser()
                            remoteKeyDao().deleteAll()
                        }
                    }
                    val prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1)
                    val nextKey = if (isEndOfList) null else page.plus(1)
                    val keys = response.results.map {
                        RemoteKey(
                            it.email,
                            prevKey = prevKey,
                            nextKey = nextKey
                        )
                    }
                    userDB.apply {
                        remoteKeyDao().insertAll(keys)
                        userDao().insertUser(response.results.mapUserRemoteToLocal())
                    }
                    MediatorResult.Success(
                        endOfPaginationReached = isEndOfList
                    )
                }
            }
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, UserLocal>
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKey = getLastRemoteKey(state)
                val nextKey = remoteKey?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKey = getFirstRemoteKey(state)
                val prevKey = remoteKey?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, UserLocal>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.email?.let { email ->
                userDB.remoteKeyDao().remoteKeysUserEmail(email)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, UserLocal>): RemoteKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { user ->
                userDB.remoteKeyDao().remoteKeysUserEmail(user.email.orEmpty())
            }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, UserLocal>): RemoteKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { user ->
                userDB.remoteKeyDao().remoteKeysUserEmail(user.email.orEmpty())
            }
    }

}