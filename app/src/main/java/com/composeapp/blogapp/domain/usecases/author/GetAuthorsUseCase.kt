package com.composeapp.blogapp.domain.usecases.author

import com.composeapp.blogapp.domain.repo.AuthorRepo
import javax.inject.Inject

class GetAuthorsUseCase @Inject constructor(
    private val authorRepo: AuthorRepo
) {

    suspend operator fun invoke(page: Int, size: Int, token: String) = authorRepo.getAllAuthors(page, size, token)
}