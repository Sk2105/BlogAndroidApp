package com.composeapp.blogapp.data.repo

import com.composeapp.blogapp.data.models.BlogAuthorModel
import com.composeapp.blogapp.data.models.User
import com.composeapp.blogapp.data.remote.AuthorServiceApi
import com.composeapp.blogapp.domain.repo.AuthorRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AuthorRepoImpl @Inject constructor(
    private val authorServiceApi: AuthorServiceApi
) : AuthorRepo {
    override suspend fun getAllAuthors(page: Int, size: Int, token: String): List<User> {
        try {
            val response = authorServiceApi.getAuthors("Bearer $token", page, size)
            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            }
        } catch (e: Exception) {
            throw Exception("Error ${e.message}")
        }
        return emptyList()
    }

    override fun getAuthorById(token: String, id: String): Flow<User> {
        return flow {
            val response = authorServiceApi.getAuthorById("Bearer $token", id)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                }
            } else {
                throw Exception("Error ${response.message()}")
            }
        }
    }

    override fun getAuthorByName(token: String, name: String): Flow<User> {
        return flow {

        }
    }

    override suspend fun getBlogsByAuthorId(
        token: String,
        id: String,
        page: Int,
    ): List<BlogAuthorModel> {
        try {
            val response = authorServiceApi.getBlogsByAuthorId("Bearer $token", id, page)
            println(response)
            if (response.isSuccessful) {
                println(response.body())
                return response.body() ?: emptyList()
            }
        } catch (e: Exception) {
            println("exception $e")
            throw Exception("Error ${e.message}")
        }

        return emptyList()
    }

}