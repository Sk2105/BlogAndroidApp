package com.composeapp.blogapp.data.remote

import com.composeapp.blogapp.data.models.BlogAuthorModel
import com.composeapp.blogapp.data.models.BlogModel
import com.composeapp.blogapp.data.models.DeleteModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BlogServiceApi {

    @GET("blogs")
    suspend fun getBlogs(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 5
    ): Response<List<BlogAuthorModel>>


    @POST("blogs")
    suspend fun createBlog(
        @Header("Authorization") token: String,
        @Body blogModel: BlogModel
    ): Response<BlogAuthorModel>

    @GET("blogs/{id}")
    suspend fun getBlogById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<BlogAuthorModel>

    @DELETE("blogs/{id}")
    suspend fun deleteBlogById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<DeleteModel>


}