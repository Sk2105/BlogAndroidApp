package com.composeapp.blogapp.presentation.viewmodel.state


sealed interface RegisterState {
    data object Loading : RegisterState
    data object Success : RegisterState
    data class Error(val message: String) : RegisterState
}

data class RegisterStateHolder(val registerState: RegisterState)