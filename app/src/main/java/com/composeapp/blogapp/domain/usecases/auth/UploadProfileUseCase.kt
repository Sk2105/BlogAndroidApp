package com.composeapp.blogapp.domain.usecases.auth

import com.composeapp.blogapp.data.models.ResponseModel
import com.composeapp.blogapp.domain.repo.AuthRepo
import com.composeapp.blogapp.domain.repo.BlogRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.JsonObject
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadProfileUseCase @Inject constructor(
    private val authRepo: AuthRepo
) {

    suspend operator fun invoke(token: String, file: MultipartBody.Part): Flow<ResponseModel> {
        return authRepo.updateProfile("Bearer $token", file)
    }
}