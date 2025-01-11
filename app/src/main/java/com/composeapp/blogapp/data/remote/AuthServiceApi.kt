package com.composeapp.blogapp.data.remote

import com.composeapp.blogapp.data.models.LoginModel
import com.composeapp.blogapp.data.models.RegisterModel
import com.composeapp.blogapp.data.models.ResponseModel
import com.composeapp.blogapp.data.models.TokenModel
import com.composeapp.blogapp.data.models.User
import kotlinx.serialization.json.JsonObject
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part


interface AuthServiceApi {

    @POST("auth/register")
    suspend fun registerUser(@Body registerModel: RegisterModel): Response<User>


    @POST("auth/login")
    suspend fun loginUser(@Body loginModel: LoginModel): Response<TokenModel>


    @GET("auth/user")
    suspend fun getUser(@Header("Authorization") token: String): Response<User>


    @PUT("auth/user/profile")
    @Multipart
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Response<ResponseModel>

}