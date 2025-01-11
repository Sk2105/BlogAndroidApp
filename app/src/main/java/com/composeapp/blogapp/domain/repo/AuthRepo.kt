package com.composeapp.blogapp.domain.repo

import com.composeapp.blogapp.data.models.LoginModel
import com.composeapp.blogapp.data.models.RegisterModel
import com.composeapp.blogapp.data.models.ResponseModel
import com.composeapp.blogapp.data.models.TokenModel
import com.composeapp.blogapp.data.models.User

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.JsonObject
import okhttp3.MultipartBody

interface AuthRepo {

    suspend fun getUserDetails(token: String): Flow<User>

    suspend fun registerUser(registerModel: RegisterModel): Flow<User>

    suspend fun loginUser(loginModel: LoginModel): Flow<TokenModel>

    suspend fun updateProfile(token: String, file: MultipartBody.Part): Flow<ResponseModel>
}