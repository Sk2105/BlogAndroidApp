package com.composeapp.blogapp.domain.usecases.auth

import com.composeapp.blogapp.data.models.RegisterModel
import com.composeapp.blogapp.data.models.User
import com.composeapp.blogapp.domain.repo.AuthRepo
import kotlinx.coroutines.flow.Flow


class RegisterUserUseCase(
    private val authRepo: AuthRepo
) {
    suspend operator fun invoke(registerModel: RegisterModel): Flow<User> {
        return authRepo.registerUser(registerModel = registerModel)
    }
}