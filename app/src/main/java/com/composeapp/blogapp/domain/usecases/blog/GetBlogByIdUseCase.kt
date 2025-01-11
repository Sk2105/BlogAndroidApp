package com.composeapp.blogapp.domain.usecases.blog

import com.composeapp.blogapp.domain.repo.BlogRepo
import javax.inject.Inject


class GetBlogByIdUseCase @Inject constructor(
    private val blogRepo: BlogRepo
) {
    suspend operator fun invoke(blogId: String, token: String) = blogRepo.getBlogById(blogId, "Bearer $token")
}