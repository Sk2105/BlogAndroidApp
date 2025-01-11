package com.composeapp.blogapp.presentation.navigation.graph

import kotlinx.serialization.Serializable


sealed interface AppGraph {

    @Serializable
    data object Login : AppGraph

    @Serializable
    data object SignUp : AppGraph

    @Serializable
    data object Home : AppGraph

    @Serializable
    data class Blog(val id: String? = null) : AppGraph

    @Serializable
    data class Profile(val id: String? = null) : AppGraph

    @Serializable
    data object Auth : AppGraph

    @Serializable
    data object HomeScreen : AppGraph

    @Serializable
    data object AddBlog : AppGraph


    @Serializable
    data class UpdateBlog(val id: String? = null) : AppGraph
}