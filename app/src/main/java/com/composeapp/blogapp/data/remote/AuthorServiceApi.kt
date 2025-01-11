package com.composeapp.blogapp.data.remote

import com.composeapp.blogapp.data.models.BlogAuthorModel
import com.composeapp.blogapp.data.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface AuthorServiceApi{

    @GET("authors")
    suspend fun getAuthors(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 5
    ):Response<List<User>>

    @GET("authors/{id}")
    suspend fun getAuthorById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ):Response<User>


    @GET("authors/{id}/blogs")
    suspend fun getBlogsByAuthorId(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 5
    ):Response<List<BlogAuthorModel>>

}