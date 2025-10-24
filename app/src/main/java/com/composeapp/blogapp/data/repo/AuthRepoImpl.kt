package com.composeapp.blogapp.data.repo

import com.composeapp.blogapp.data.models.ErrorModel
import com.composeapp.blogapp.data.models.LoginModel
import com.composeapp.blogapp.data.models.RegisterModel
import com.composeapp.blogapp.data.models.ResponseModel
import com.composeapp.blogapp.data.models.TokenModel
import com.composeapp.blogapp.data.models.User
import com.composeapp.blogapp.data.remote.AuthServiceApi
import com.composeapp.blogapp.domain.repo.AuthRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import okhttp3.MultipartBody


class AuthRepoImpl(
    private val authServiceApi: AuthServiceApi
) : AuthRepo {

    override suspend fun getUserDetails(token: String): Flow<User> {
        return flow {
            val response = authServiceApi.getUser(token)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                }
            } else {
                val errorResponse = response.errorBody()
                println("getUserDetails error: $errorResponse")
            }
        };
    }

    override suspend fun registerUser(registerModel: RegisterModel): Flow<User> {
        return flow {
            println("registerUser called")
            val response = authServiceApi.registerUser(registerModel)
            println("registerUser response: $response")
            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            } else {
                val errorResponse = response.errorBody()
                println("registerUser error: $errorResponse")
                val errorMessage =
                    Json.decodeFromString<ErrorModel>(errorResponse!!.string())
                println("registerUser errorMessage: ${errorMessage.error}")
                throw Exception(errorMessage.error)

            }
        }
    }

    override suspend fun loginUser(loginModel: LoginModel): Flow<TokenModel> {
        return flow {
            val response = authServiceApi.loginUser(loginModel)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                }
            } else {
                val errorResponse = response.errorBody()
                println("loginUser error: $errorResponse")
                val errorMessage =
                    Json.decodeFromString<ErrorModel>(errorResponse!!.string())
                println("loginUser errorMessage: ${errorMessage.error}")
                throw Exception("Something went wrong")
            }
        }
    }

    override suspend fun updateProfile(
        token: String,
        file: MultipartBody.Part
    ): Flow<ResponseModel> {
        return flow {
            val response = authServiceApi.updateProfile(token, file)

            println("updateProfile response: $response")

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                }
            } else {
                val errorResponse = response.errorBody()
                println("updateProfile error: $errorResponse")
                val errorMessage =
                    Json.decodeFromString<ErrorModel>(errorResponse!!.string())
                println("updateProfile errorMessage: ${errorMessage.error}")
                throw Exception(errorMessage.error)
            }
        }
    }


}