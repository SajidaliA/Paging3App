package com.example.paging3app.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.paging3app.local.UserDatabase
import com.example.paging3app.local.UserRemoteKeys
import com.example.paging3app.model.User
import com.example.paging3app.remote.UserAPI

@ExperimentalPagingApi
class UserRemoteMediator(
    private val userAPI: UserAPI,
    private val userDatabase: UserDatabase
) : RemoteMediator<Int, User>() {

    private val userDao = userDatabase.getUserDao()
    private val remoteKeysDao = userDatabase.getRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, User>): MediatorResult {

        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(10) ?: 0
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeysFromFirstItem(state)
                    val prevPage = remoteKeys?.previousKey
                        ?: MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyFromLatItem(state)
                    val nextPage = remoteKeys?.nextKey
                        ?: MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }
            //Fetch remote API
            val response = userAPI.getUsers(skip = currentPage as Int)
            val endOfPaginationReached = currentPage > response.total

            val previousPage = if (currentPage == 0) null else currentPage - 10
            val nextPage = if (endOfPaginationReached) null else currentPage + 10

            // Save users and remote keys to DB
            userDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    userDao.deleteUsers()
                    remoteKeysDao.deleteAllRemoteKeys()
                }

                userDao.insertUser(response.users)
                val keys = response.users.map { user ->
                    UserRemoteKeys(id = user.id, previousKey = previousPage, nextKey = nextPage)
                }
                remoteKeysDao.insertRemoteKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached)

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    //Loading for state : Refresh, Prepend, Append

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, User>): UserRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeysFromFirstItem(state: PagingState<Int, User>): UserRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { user ->
                remoteKeysDao.getRemoteKeys(user.id)
            }
    }

    private suspend fun getRemoteKeyFromLatItem(state: PagingState<Int, User>): UserRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { user ->
                remoteKeysDao.getRemoteKeys(user.id)
            }
    }
}