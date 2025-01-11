package com.composeapp.blogapp.data.models

data class BlogAuthorModel(
    val id: String,
    val content:String,
    val title:String,
    val author: User
)