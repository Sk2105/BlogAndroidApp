package com.composeapp.blogapp.domain.usecases.local

import com.composeapp.blogapp.data.local.TokenRepository
import kotlinx.coroutines.flow.Flow

class GetUserTokenUseCase(
    private val repository: TokenRepository
) {

    operator fun invoke(): Flow<String> {
        return repository.accessTokenFlow
    }
}