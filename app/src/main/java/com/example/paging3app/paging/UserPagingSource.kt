package com.example.paging3app.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging3app.model.User
import com.example.paging3app.remote.UserAPI

class UserPagingSource(private val userAPI: UserAPI) : PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(10) ?:
            state.closestPageToPosition(it)?.nextKey?.minus(10)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val skip = params.key ?: 0
            val response = userAPI.getUsers(limit = 10, skip = skip)
            LoadResult.Page(
                data = response.users,
                prevKey = if (skip == 0) null else skip - 10,
                nextKey = if (skip < response.total) skip + 10 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}