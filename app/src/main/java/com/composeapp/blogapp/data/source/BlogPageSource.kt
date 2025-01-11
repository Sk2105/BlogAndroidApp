package com.composeapp.blogapp.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.composeapp.blogapp.data.models.BlogAuthorModel
import com.composeapp.blogapp.domain.repo.AuthorRepo
import com.composeapp.blogapp.domain.repo.BlogRepo
import com.composeapp.blogapp.domain.usecases.blog.GetAllBlogUseCase


class BlogPageSource(
    private val blogRepo: BlogRepo? = null,
    private val token: String,
    private val authorId: String? = null,
    private val authorRepo: AuthorRepo? = null
) : PagingSource<Int, BlogAuthorModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BlogAuthorModel> {
        return try {
            val page = params.key ?: 0
            println(
                "authorId $authorId page $page token ${"Bearer $token"} authorRepo $authorRepo"
            )
            val response = if (authorId == null) {
                blogRepo?.getBlogs(page = page, token = "Bearer $token")
            } else {
                authorRepo?.getBlogsByAuthorId(
                    page = page,
                    token = token,
                    id = authorId
                )
            }

            if (response == null) {
                throw Exception("Response is null")
            }
            LoadResult.Page(
                data = response,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            println("exception $e")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BlogAuthorModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

}