package com.composeapp.blogapp.domain.usecases.local

import com.composeapp.blogapp.data.local.TokenRepository

class SaveUserTokenUseCase(
    private val tokenRepository: TokenRepository
) {
     suspend operator fun invoke(accessToken: String) = tokenRepository.saveAccessToken(accessToken)
}