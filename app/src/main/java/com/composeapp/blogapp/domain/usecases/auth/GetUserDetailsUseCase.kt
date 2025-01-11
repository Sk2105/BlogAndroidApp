package com.composeapp.blogapp.domain.usecases.auth

import com.composeapp.blogapp.data.models.User
import com.composeapp.blogapp.domain.repo.AuthRepo
import kotlinx.coroutines.flow.Flow


class GetUserDetailsUseCase(
    private val repository: AuthRepo
) {
    suspend operator fun invoke(token: String): Flow<User> = repository.getUserDetails(token="Bearer $token")

}