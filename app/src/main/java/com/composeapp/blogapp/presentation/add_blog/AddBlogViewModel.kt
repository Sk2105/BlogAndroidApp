package com.composeapp.blogapp.presentation.add_blog

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composeapp.blogapp.data.models.BlogModel
import com.composeapp.blogapp.domain.usecases.blog.SaveBlogUseCase
import com.composeapp.blogapp.domain.usecases.local.GetUserTokenUseCase
import com.composeapp.blogapp.presentation.viewmodel.state.SaveBlogState
import com.composeapp.blogapp.presentation.viewmodel.state.SaveBlogStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddBlogViewModel @Inject constructor(
    private val saveBlogUseCase: SaveBlogUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase
):ViewModel(){

    private val _saveChannel = Channel<SaveBlogStateHolder>()
    val saveChannel = _saveChannel.receiveAsFlow()

    private val userToken = mutableStateOf("")
    init {
        viewModelScope.launch {
            getUserTokenUseCase().collect {
                userToken.value = it
            }
        }
    }

    fun saveNewBlog(blog: BlogModel) = viewModelScope.launch {
        saveBlogUseCase(blog, userToken.value)
            .catch {
                _saveChannel.send(SaveBlogStateHolder(SaveBlogState.Error(it.message.toString())))
            }.collect {
                _saveChannel.send(SaveBlogStateHolder(SaveBlogState.Success))
            }
    }
}