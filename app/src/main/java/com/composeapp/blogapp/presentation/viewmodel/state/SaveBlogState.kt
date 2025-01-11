package com.composeapp.blogapp.presentation.viewmodel.state


sealed interface SaveBlogState {
    data object Success : SaveBlogState
    data class Error(val message: String) : SaveBlogState

}

data class SaveBlogStateHolder(
    val state: SaveBlogState?
)