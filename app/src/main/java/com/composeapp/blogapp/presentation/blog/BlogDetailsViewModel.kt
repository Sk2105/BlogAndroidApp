package com.composeapp.blogapp.presentation.blog

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composeapp.blogapp.data.models.User
import com.composeapp.blogapp.domain.usecases.auth.GetUserDetailsUseCase
import com.composeapp.blogapp.domain.usecases.blog.DeleteBlogUseCase
import com.composeapp.blogapp.domain.usecases.blog.GetBlogByIdUseCase
import com.composeapp.blogapp.domain.usecases.local.GetUserTokenUseCase
import com.composeapp.blogapp.presentation.blog.state.BlogState
import com.composeapp.blogapp.presentation.blog.state.BlogStateHolder
import com.composeapp.blogapp.presentation.blog.state.DeleteState
import com.composeapp.blogapp.presentation.blog.state.DeleteStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BlogDetailsViewModel @Inject constructor(
    private val getBlogByIdUseCase: GetBlogByIdUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val deleteBlogUseCase: DeleteBlogUseCase
) : ViewModel() {
    private val _blog = MutableStateFlow(BlogStateHolder(BlogState.Loading))

    val blog = _blog.asStateFlow()

    private val userToken = mutableStateOf("")

    private val _userDetails = mutableStateOf<User?>(null)

    val userDetails = _userDetails

    private val _deleteState = Channel<DeleteStateHolder>()
    val deleteState = _deleteState.receiveAsFlow()

    init {
        viewModelScope.launch {
            getUserTokenUseCase().collect {
                userToken.value = it

               fetchUserDetails()
            }
        }
    }

    private fun fetchUserDetails() {
        viewModelScope.launch {
            getUserDetailsUseCase(userToken.value).collect {
                _userDetails.value = it
            }
        }
    }


    suspend fun fetchBlog(id: String) = getUserTokenUseCase().collect { token ->
        getBlogByIdUseCase(
            token = token,
            blogId = id
        ).catch {
            _blog.value = BlogStateHolder(BlogState.Error(it.message.toString()))
        }.collect {
            _blog.value = BlogStateHolder(BlogState.Success(it))
        }
    }

    fun deleteBlog(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _deleteState.send(DeleteStateHolder(DeleteState.Loading))
        deleteBlogUseCase(blogId = id, userToken.value).catch {
            _deleteState.send(DeleteStateHolder(DeleteState.Error(it.message.toString())))
        }.collect {
            _deleteState.send(DeleteStateHolder(DeleteState.Success))
        }
    }
}