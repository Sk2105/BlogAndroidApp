package com.composeapp.blogapp.presentation.blog.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.composeapp.blogapp.presentation.blog.BlogDetailsViewModel
import com.composeapp.blogapp.presentation.blog.components.AuthorProfileSection
import com.composeapp.blogapp.presentation.blog.components.BlogDetailsTopBar
import com.composeapp.blogapp.presentation.blog.components.ContentSection
import com.composeapp.blogapp.presentation.blog.components.TitleSection
import com.composeapp.blogapp.presentation.blog.dialog.DeleteDialog
import com.composeapp.blogapp.presentation.blog.state.BlogState
import com.composeapp.blogapp.presentation.blog.state.DeleteState
import com.composeapp.blogapp.presentation.blog.state.DeleteStateHolder
import com.composeapp.blogapp.presentation.common.SimmerLoader
import com.composeapp.blogapp.presentation.navigation.graph.AppGraph


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogDetailScreen(
    blogId: String,
    navigator: NavHostController
) {
    val viewModel: BlogDetailsViewModel = hiltViewModel()
    var isShowEditButton by remember {
        mutableStateOf(false)
    }
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }



    LaunchedEffect(key1 = viewModel) {
        viewModel.fetchBlog(blogId)

    }
    val userDetails = viewModel.userDetails.value

    val deleteState = viewModel.deleteState.collectAsState(DeleteStateHolder(DeleteState.None))
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )

    val blog = viewModel.blog.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(key1 = deleteState.value.state) {
        when (deleteState.value.state) {

            is DeleteState.Error -> {
                val error = (deleteState.value.state as DeleteState.Error).message
                Toast.makeText(context, "Found error $error", Toast.LENGTH_SHORT).show()
                showDeleteDialog = false
            }

            DeleteState.Loading -> {

            }

            DeleteState.None -> {

            }

            DeleteState.Success -> {
                Toast.makeText(
                    context,
                    "Blog Successfully delete",
                    Toast.LENGTH_SHORT
                ).show()
                showDeleteDialog = false
                navigator.popBackStack()

            }
        }
    }
    Scaffold(
        topBar = {

            BlogDetailsTopBar(
                scrollBehavior = scrollBehavior,
                onEditButtonClicked = {
                    navigator.navigate(AppGraph.AddBlog)
                },
                isShowEditButton = isShowEditButton,
                onBackPress = {
                    navigator.popBackStack()
                }){
                showDeleteDialog = true
            }


        },
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize()
            .background(
                color = Color(0xff212121)
            )
    ) {

        if (showDeleteDialog) {
            DeleteDialog(onConfirm = {
                viewModel.deleteBlog(blogId)
            }) {
                showDeleteDialog = false
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
                .background(
                    color = Color(0xff212121)
                )
        ) {

            when (blog.value.state) {
                is BlogState.Error -> {
                    val error = (blog.value.state as BlogState.Error).error
                    Text(
                        text = error,
                        color = Color.Red
                    )
                }

                BlogState.Loading -> {
                    SimmerLoader(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 8.dp)
                            .height(60.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    SimmerLoader(
                        modifier = Modifier
                            .width(200.dp)
                            .padding(vertical = 8.dp, horizontal = 8.dp)
                            .height(60.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    SimmerLoader(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 8.dp)
                            .height(80.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )

                    SimmerLoader(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp, horizontal = 8.dp)
                            .height(10.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    SimmerLoader(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp, horizontal = 8.dp)
                            .height(10.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    SimmerLoader(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp, horizontal = 8.dp)
                            .height(10.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    SimmerLoader(
                        modifier = Modifier
                            .width(200.dp)
                            .padding(vertical = 2.dp, horizontal = 8.dp)
                            .height(10.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )


                }

                is BlogState.Success -> {

                    val b = (blog.value.state as BlogState.Success).blog
                    userDetails?.let { author ->
                        isShowEditButton = author.id == b.author.id
                    }
                    TitleSection(b.title)

                    AuthorProfileSection(b.author) { authorId ->
                        navigator.navigate(AppGraph.Profile(authorId))
                    }

                    ContentSection(b)
                }
            }


        }

    }
}