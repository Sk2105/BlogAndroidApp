package com.composeapp.blogapp.presentation.profile.state

import com.composeapp.blogapp.data.models.User


sealed interface AuthorState{

    data object Loading: AuthorState
    data class Success(val author:User): AuthorState
    data class Error(val message:String): AuthorState
}

data class AuthorStateHolder(
    val state: AuthorState = AuthorState.Loading
)