package com.composeapp.blogapp.presentation.signup.state

sealed interface RegisterState {
    data object Nothing : RegisterState
    data object Loading : RegisterState
    data object Success : RegisterState
    data class Error(val message: String) : RegisterState
}

data class RegisterStateHolder(val registerState: RegisterState = RegisterState.Nothing)