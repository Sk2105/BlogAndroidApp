package com.composeapp.blogapp.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.composeapp.blogapp.data.models.User
import com.composeapp.blogapp.domain.repo.AuthorRepo
import com.composeapp.blogapp.domain.usecases.author.GetAuthorsUseCase

class AuthorPageSource(
    private val authorRepo: AuthorRepo,
    private val token: String
) : PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 0
            val response = authorRepo.getAllAuthors(page = page, size = 10, token = token)
            println("token $token")
            println("Author response $response")
            LoadResult.Page(
                data = response,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}