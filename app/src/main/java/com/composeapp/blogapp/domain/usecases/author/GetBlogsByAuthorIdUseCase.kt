package com.composeapp.blogapp.domain.usecases.author

import com.composeapp.blogapp.domain.repo.AuthorRepo
import javax.inject.Inject


class GetBlogsByAuthorIdUseCase @Inject constructor(private val authorRepo: AuthorRepo) {
    suspend operator fun invoke(
        token: String,
        id: String,
        page: Int
    ) =
        authorRepo.getBlogsByAuthorId(token, id, page)


}