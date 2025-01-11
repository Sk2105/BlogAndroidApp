package com.composeapp.blogapp.domain.usecases.blog

import com.composeapp.blogapp.data.models.BlogAuthorModel
import com.composeapp.blogapp.data.models.BlogModel
import com.composeapp.blogapp.domain.repo.BlogRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SaveBlogUseCase @Inject constructor(
    private val blogRepo: BlogRepo
) {
    suspend operator fun invoke(blog: BlogModel, token: String): Flow<BlogAuthorModel> =
        blogRepo.saveBlog(blog, token = "Bearer $token")
}