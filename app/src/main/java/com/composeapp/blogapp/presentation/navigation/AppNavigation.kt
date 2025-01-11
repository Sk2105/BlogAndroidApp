@file:Suppress("NAME_SHADOWING")

package com.composeapp.blogapp.presentation.navigation

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.composeapp.blogapp.presentation.add_blog.screen.AddBlogScreen
import com.composeapp.blogapp.presentation.blog.screen.BlogDetailScreen
import com.composeapp.blogapp.presentation.home.screen.HomeScreen
import com.composeapp.blogapp.presentation.login.LoginScreen
import com.composeapp.blogapp.presentation.navigation.graph.AppGraph
import com.composeapp.blogapp.presentation.profile.screen.ProfileScreen
import com.composeapp.blogapp.presentation.signup.screen.SignUpScreen
import com.composeapp.blogapp.presentation.update_blog.UpdateBlogScreen
import com.composeapp.blogapp.presentation.viewmodel.MainViewModel


@Composable
fun AppNavigation(
    route: AppGraph,
    viewModel: MainViewModel
) {

    val navController = rememberNavController()
    val duration: FiniteAnimationSpec<IntOffset> = tween(durationMillis = 500)

    NavHost(navController = navController, startDestination = route,
        popExitTransition = {
            slideOutHorizontally(
                animationSpec = duration,
                targetOffsetX = { it }
            )

        },
        popEnterTransition = {
            slideInHorizontally(
                animationSpec = duration,
                initialOffsetX = { -it }
            )

        }, exitTransition = {
            slideOutHorizontally(
                animationSpec = duration,
                targetOffsetX = { -it }
            )

        }, enterTransition = {
            slideInHorizontally(
                animationSpec = duration,
                initialOffsetX = { it }
            )

        }) {

        navigation<AppGraph.HomeScreen>(startDestination = AppGraph.Home) {

            composable<AppGraph.Home> {
                HomeScreen(navigation = navController)
            }

            composable<AppGraph.Profile> {
                val route = it.toRoute<AppGraph.Profile>()
                ProfileScreen(
                    authorId = route.id.toString(),
                    navigator = navController
                )
            }

            composable<AppGraph.Blog> {
                val route = it.toRoute<AppGraph.Blog>()
                println(route.id)
                BlogDetailScreen(
                    blogId = route.id.toString(),
                    navigator = navController
                )
            }

            composable<AppGraph.AddBlog> {
                AddBlogScreen(
                    navigator = navController
                )
            }

            composable<AppGraph.UpdateBlog> {
                UpdateBlogScreen(
                    navigator = navController
                )
            }


        }

        navigation<AppGraph.Auth>(startDestination = AppGraph.Login) {
            composable<AppGraph.Login> {
                LoginScreen(
                    navigator = navController,
                    viewModel = viewModel
                )
            }
            composable<AppGraph.SignUp> {
                SignUpScreen(
                    navigator = navController
                )
            }
        }

    }
}