package com.composeapp.blogapp.presentation.home.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.composeapp.blogapp.presentation.common.BlogsSection
import com.composeapp.blogapp.presentation.common.SimmerLoader
import com.composeapp.blogapp.presentation.home.HomeViewModel
import com.composeapp.blogapp.presentation.home.components.AuthorSection
import com.composeapp.blogapp.presentation.home.components.HomeTopBar
import com.composeapp.blogapp.presentation.navigation.graph.AppGraph


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navigation: NavHostController) {

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    val homeViewModel: HomeViewModel = hiltViewModel()
    val blogsPager = homeViewModel.blogPage.collectAsState().value.collectAsLazyPagingItems()
    val authorPager = homeViewModel.authorPage.collectAsState().value.collectAsLazyPagingItems()
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = Unit) {
        homeViewModel.reload()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeTopBar(
                scrollBehavior = scrollBehavior
            ) {
                val userDetails = homeViewModel.userDetails.collectAsState().value
                userDetails?.let {
                    IconButton(onClick = {
                        navigation.navigate(AppGraph.Profile(userDetails.id))
                    }) {
                        AsyncImage(
                            model = it.imageUrl
                                ?: "https://plus.unsplash.com/premium_photo-1689568126014-06fea9d5d341?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                            contentDescription = "",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(100.dp))
                                .border(
                                    1.dp,
                                    color = Color(0xfffffc107),
                                    RoundedCornerShape(100.dp)
                                ),
                            contentScale = ContentScale.FillBounds
                        )

                    }

                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(
                        text = "Add New Blog",
                        fontSize = 18.sp, modifier = Modifier.animateContentSize()
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Localized description"
                    )
                },
                onClick = {
                    navigation.navigate(AppGraph.AddBlog)
                },
                containerColor = Color(0xffffc107),
                contentColor = Color.Black,
                modifier = Modifier.animateContentSize(),
                expanded = !scrollState.isScrollInProgress

            )
        },
        containerColor = Color("#212121".toColorInt())
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            Text(
                text = "Top Author's",
                fontSize = 16.sp,
                color = Color(0xffffc107),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )

            Row {
                val authorsList = authorPager.itemSnapshotList.items
                if (authorsList.isNotEmpty()) {
                    AuthorSection(authorPager.itemSnapshotList.items) {
                        navigation.navigate(AppGraph.Profile(it))
                    }
                }

                authorPager.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            //You can add modifier to manage load state when first time response page is loading
                            (0..5).forEach { _ ->
                                SimmerLoader(
                                    Modifier
                                        .padding(8.dp)
                                        .size(100.dp)
                                        .clip(RoundedCornerShape(100.dp))
                                        .fillMaxSize()
                                )
                            }

                        }

                        loadState.append is LoadState.Loading -> {
                            //You can add modifier to manage load state when next response page is loading
                            (0..5).forEach { _ ->
                                SimmerLoader(
                                    Modifier
                                        .padding(8.dp)
                                        .size(100.dp)
                                        .clip(RoundedCornerShape(100.dp))
                                        .fillMaxSize()
                                )
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            val error = loadState.append as LoadState.Error
                            Text(
                                text = error.error.message.toString(),
                                modifier = Modifier.padding(8.dp),
                                color = Color.White
                            )
                        }
                    }
                }
            }


            Text(
                text = "Blogs",
                fontSize = 16.sp,
                color = Color(0xffffc107),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )




            BlogsSection(list = blogsPager.itemSnapshotList.items) {
                navigation.navigate(AppGraph.Blog(it))
            }

            blogsPager.apply {
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