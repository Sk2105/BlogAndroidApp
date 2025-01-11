package com.composeapp.blogapp.domain.repo

import com.composeapp.blogapp.data.models.BlogAuthorModel
import com.composeapp.blogapp.data.models.BlogModel
import com.composeapp.blogapp.data.models.DeleteModel
import kotlinx.coroutines.flow.Flow

interface BlogRepo {

    suspend fun getBlogs(token: String,page:Int): List<BlogAuthorModel>

    suspend fun saveBlog(blog: BlogModel, token: String): Flow<BlogAuthorModel>

    suspend fun getBlogById(blogId: String,token: String): Flow<BlogAuthorModel>


    suspend fun deleteBlogById(blogId: String,token: String): Flow<DeleteModel>
}