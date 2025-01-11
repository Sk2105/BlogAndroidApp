package com.composeapp.blogapp.domain.repo

import com.composeapp.blogapp.data.models.BlogAuthorModel
import com.composeapp.blogapp.data.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.JsonObject


interface AuthorRepo {

   suspend fun getAllAuthors(
        page: Int,
        size: Int,
        token: String
    ): List<User>

    fun getAuthorById(token: String, id: String): Flow<User>

    fun getAuthorByName(token: String, name: String): Flow<User>

    suspend fun getBlogsByAuthorId(token: String, id: String, page: Int): List<BlogAuthorModel>


}