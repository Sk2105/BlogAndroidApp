package com.composeapp.blogapp.presentation.viewmodel.state

import com.composeapp.blogapp.data.models.User


sealed interface UserDetailsState {
    data object Loading : UserDetailsState
    data class Success(val user: User) : UserDetailsState
    data class Error(val message: String) : UserDetailsState
}


data class UserDetailsStateHolder(val state: UserDetailsState)