package com.composeapp.blogapp.presentation.profile.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.composeapp.blogapp.presentation.common.BlogsSection
import com.composeapp.blogapp.presentation.common.SimmerLoader
import com.composeapp.blogapp.presentation.navigation.graph.AppGraph
import com.composeapp.blogapp.presentation.profile.ProfileViewModel
import com.composeapp.blogapp.presentation.profile.components.ProfileSection
import com.composeapp.blogapp.presentation.profile.components.ProfileTopBar
import com.composeapp.blogapp.presentation.profile.state.AuthorState
import com.composeapp.blogapp.presentation.profile.state.UploadState
import com.composeapp.blogapp.presentation.profile.state.UploadStateHolder
import com.composeapp.blogapp.presentation.viewmodel.MainViewModel
import com.composeapp.blogapp.presentation.viewmodel.state.UserDetailsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(authorId: String, navigator: NavHostController) {


    val context = LocalContext.current
    val profileViewModel: ProfileViewModel = hiltViewModel()
    LaunchedEffect(key1 = authorId) {
        profileViewModel.fetchAuthorByIdDetails(authorId)
    }
    val userDetailsState = profileViewModel.author.collectAsState().value
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )

    val blogsState = profileViewModel.blogPage.collectAsState().value.collectAsLazyPagingItems()

    val uploadingState = profileViewModel.uploadingStatus.collectAsState(
        initial = UploadStateHolder(
            UploadState.Idle
        )
    ).value
    LaunchedEffect(key1 = uploadingState) {
        when (uploadingState.uploadState) {
            is UploadState.Error -> {
                val error = uploadingState.uploadState.message
                Toast.makeText(
                    context,
                    error,
                    Toast.LENGTH_SHORT
                ).show()
            }

            UploadState.Idle -> {

            }

            UploadState.Progress -> {
                Toast.makeText(
                    context,
                    "Uploading",
                    Toast.LENGTH_SHORT
                ).show()
            }

            UploadState.Success -> {
                profileViewModel.fetchAuthorByIdDetails(authorId)
                Toast.makeText(
                    context,
                    "Uploaded",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
    Scaffold(
        topBar = {
            ProfileTopBar(
                isShowEditButton = profileViewModel.userDetails.value?.id == authorId,
                scrollBehavior = scrollBehavior, signOutPressed = {
                    profileViewModel.signOut()
                    // clear all backstack entry
                    navigator.popBackStack(0, true)
                    navigator.navigate(AppGraph.Auth)
                }) {
                navigator.popBackStack()
            }
        },
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(Color(0xff212121))
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {

            when (userDetailsState.state) {
                is AuthorState.Success -> {
                    val user = userDetailsState.state.author
                    ProfileSection(user)
                }

                is AuthorState.Error -> {
                    val error = userDetailsState.state.message
                    Text(
                        text = error,
                        modifier = Modifier.padding(8.dp),
                        color = Color.Red
                    )

                }

                is AuthorState.Loading -> {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            SimmerLoader(
                                Modifier
                                    .size(120.dp)
                                    .padding(12.dp)
                                    .clip(RoundedCornerShape(100.dp))

                            )
                            SimmerLoader(
                                Modifier
                                    .width(120.dp)
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .height(10.dp)
                            )
                            SimmerLoader(
                                Modifier
                                    .width(160.dp)
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .height(10.dp)
                            )
                        }
                    }

                }

            }

            Text(
                text = "Blog's",
                modifier = Modifier.padding(8.dp),
                color = Color(0xffFFC107)
            )

            val blogs = blogsState.itemSnapshotList.items

            if (blogs.isNotEmpty()) {
                BlogsSection(blogs) {
                    navigator.navigate(AppGraph.Blog(it))
                }
            }

            blogsState.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        //You can add modifier to manage load state when first time response page is loading
                        (0..6).forEach { _ ->
                            SimmerLoader(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .height(180.dp)
                            )
                        }

                    }

                    loadState.append is LoadState.Loading -> {
                        //You can add modifier to manage load state when next response page is loading
                        (0..6).forEach { _ ->
                            SimmerLoader(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .height(180.dp)
                            )
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        //You can use modifier to show error message
                        val error = loadState.append as LoadState.Error
                        //You can use modifier to show error message
                        Text(
                            text = error.error.message.toString(),
                            modifier = Modifier.padding(8.dp),
                            color = Color.White
                        )

                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xffffc107),
                                contentColor = Color.Black
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .padding(8.dp)
                        ) {
                            Text(text = "Retry")
                        }

                    }
                }
            }


        }
    }

}