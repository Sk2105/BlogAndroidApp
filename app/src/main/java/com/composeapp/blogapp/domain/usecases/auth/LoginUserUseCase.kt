package com.composeapp.blogapp.domain.usecases.auth

import com.composeapp.blogapp.data.models.LoginModel
import com.composeapp.blogapp.data.models.TokenModel
import com.composeapp.blogapp.domain.repo.AuthRepo
import kotlinx.coroutines.flow.Flow


class LoginUserUseCase(
    private val repository: AuthRepo
) {
    suspend operator fun invoke(loginModel: LoginModel): Flow<TokenModel> =
        repository.loginUser(loginModel)
}