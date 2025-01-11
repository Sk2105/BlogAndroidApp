package com.composeapp.blogapp.presentation.viewmodel.state

import com.composeapp.blogapp.data.models.BlogAuthorModel


sealed interface GetBlogsState {

    data object Loading : GetBlogsState
    data class Success(val list: List<BlogAuthorModel>) : GetBlogsState
    data class Error(val error: String) : GetBlogsState

}

data class GetBlogsStateHolder(val state: GetBlogsState)