package com.composeapp.blogapp.data.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames


@Serializable
data class ErrorModel(
    val code: Int,
    val error: String,
)