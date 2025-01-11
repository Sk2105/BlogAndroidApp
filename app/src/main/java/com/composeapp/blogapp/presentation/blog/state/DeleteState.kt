package com.composeapp.blogapp.presentation.blog.state


sealed interface DeleteState {
    data object None:DeleteState
    data object Loading:DeleteState
    data object Success:DeleteState
    data class Error(val message:String):DeleteState
}

data class DeleteStateHolder(
    val state: DeleteState = DeleteState.None
)