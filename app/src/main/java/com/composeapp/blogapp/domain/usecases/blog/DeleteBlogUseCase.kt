package com.composeapp.blogapp.domain.usecases.blog

import com.composeapp.blogapp.domain.repo.BlogRepo
import javax.inject.Inject


class DeleteBlogUseCase @Inject constructor(
    private val repo: BlogRepo
) {
   suspend operator fun invoke(blogId: String, token: String) = repo.deleteBlogById(
        blogId = blogId,
        token = "Bearer $token"
    )
}