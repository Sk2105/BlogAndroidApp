package com.composeapp.blogapp.presentation.profile.state

sealed interface UploadState {
    data object Idle : UploadState
    data object Progress : UploadState
    data object Success : UploadState
    class Error(val message: String) : UploadState
}

data class UploadStateHolder(
    val uploadState: UploadState = UploadState.Idle
)