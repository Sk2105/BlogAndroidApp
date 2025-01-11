package com.composeapp.blogapp.domain.usecases.author

import com.composeapp.blogapp.domain.repo.AuthorRepo
import javax.inject.Inject


class GetAuthorByIdUseCase @Inject constructor(
    private val authorRepo: AuthorRepo
) {
    operator fun invoke(
        token: String, id:
        String
    ) = authorRepo.getAuthorById(token, id)
}