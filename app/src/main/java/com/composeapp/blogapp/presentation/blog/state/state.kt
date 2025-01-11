package com.composeapp.blogapp.presentation.blog.state

import com.composeapp.blogapp.data.models.BlogAuthorModel

sealed interface BlogState{
    data object Loading: BlogState

    data class Success(val blog:BlogAuthorModel): BlogState

    data class Error(val error:String): BlogState

}


data class BlogStateHolder(
    val state: BlogState
)