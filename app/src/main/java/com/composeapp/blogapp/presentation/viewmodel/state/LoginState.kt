package com.composeapp.blogapp.presentation.viewmodel.state

sealed interface LoginState{

    data object LoginLoading : LoginState
    data class LoginSuccess(val token: String) : LoginState
    data class LoginError(val error: String) : LoginState


}


data class LoginStateHolder(val loginState: LoginState)