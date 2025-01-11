package com.composeapp.blogapp.domain.usecases.blog

import com.composeapp.blogapp.data.models.BlogAuthorModel
import com.composeapp.blogapp.data.source.AuthorPageSource
import com.composeapp.blogapp.domain.repo.BlogRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAllBlogUseCase @Inject constructor(
    private val blogRepo: BlogRepo
) {
    suspend operator fun invoke(token: String,page: Int): List<BlogAuthorModel> =
        blogRepo.getBlogs(token = "Bearer $token", page = page)
}