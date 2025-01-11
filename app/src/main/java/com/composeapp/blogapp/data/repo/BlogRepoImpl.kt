package com.composeapp.blogapp.data.repo

import com.composeapp.blogapp.data.models.BlogAuthorModel
import com.composeapp.blogapp.data.models.BlogModel
import com.composeapp.blogapp.data.models.DeleteModel
import com.composeapp.blogapp.data.models.ErrorModel
import com.composeapp.blogapp.data.remote.BlogServiceApi
import com.composeapp.blogapp.domain.repo.BlogRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import java.net.SocketTimeoutException


class BlogRepoImpl(private val blogServiceApi: BlogServiceApi) : BlogRepo {
    override suspend fun getBlogs(token: String, page: Int): List<BlogAuthorModel> {
        try {
            val response = blogServiceApi.getBlogs(token, page)
            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            }
        } catch (e: SocketTimeoutException) {
            println(e.message)
        } catch (e: Exception) {
            println(e.message)
        }
        return emptyList()
    }

    override suspend fun saveBlog(blog: BlogModel, token: String): Flow<BlogAuthorModel> {
        return flow {
            val response = blogServiceApi.createBlog(
                token,
                blog
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                }
            } else {

                throw Exception(response.message())
            }
        }
    }

    override suspend fun getBlogById(blogId: String, token: String): Flow<BlogAuthorModel> {
        return flow {
            val response = blogServiceApi.getBlogById(id = blogId, token = token)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                }
            } else {

                throw Exception(response.message())
            }
        }
    }

    override suspend fun deleteBlogById(blogId: String, token: String): Flow<DeleteModel> {
        return flow {
            val response = blogServiceApi.deleteBlogById(id = blogId, token = token)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                }
            } else {
                throw Exception(response.errorBody()?.string())
            }
        }
    }

}