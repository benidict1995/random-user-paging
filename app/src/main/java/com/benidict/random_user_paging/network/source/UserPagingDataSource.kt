package com.benidict.random_user_paging.network.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.benidict.random_user_paging.domain.mapper.mapUserRemoteToDomain
import com.benidict.random_user_paging.domain.model.User
import com.benidict.random_user_paging.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class UserPagingDataSource(
    private val apiService: ApiService
) : PagingSource<Int, User>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            withContext(Dispatchers.IO) {
                val response = apiService.getUsers(
                    page = page,
                    results = params.loadSize
                ).results.mapUserRemoteToDomain()
                LoadResult.Page(
                    data = response,
                    prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1),
                    nextKey = if (response.isEmpty()) null else page.plus(1)
                )
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? =
        state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
}