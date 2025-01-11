package com.composeapp.blogapp.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.composeapp.blogapp.data.models.BlogAuthorModel
import com.composeapp.blogapp.data.models.User
import com.composeapp.blogapp.data.source.AuthorPageSource
import com.composeapp.blogapp.data.source.BlogPageSource
import com.composeapp.blogapp.domain.repo.AuthorRepo
import com.composeapp.blogapp.domain.repo.BlogRepo
import com.composeapp.blogapp.domain.usecases.auth.GetUserDetailsUseCase
import com.composeapp.blogapp.domain.usecases.author.GetAuthorsUseCase
import com.composeapp.blogapp.domain.usecases.blog.GetAllBlogUseCase
import com.composeapp.blogapp.domain.usecases.local.GetUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject
constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val authorRepo: AuthorRepo,
    private val blogRepo: BlogRepo
) : ViewModel() {

    // State to hold the user token
    private val _userToken = mutableStateOf("")
    val userToken = _userToken

    // State to hold the Pager for authors
    private val _authorPage = MutableStateFlow(emptyList<PagingData<User>>().asFlow())
    val authorPage = _authorPage

    // State to hold the Pager for blogs
    private val _blogPage = MutableStateFlow(emptyList<PagingData<BlogAuthorModel>>().asFlow())
    val blogPage = _blogPage

    // State to hold the user details
    private val _userDetails = MutableStateFlow<User?>(null)
    val userDetails = _userDetails

    init {
        fetchUserToken()
    }


    fun reload() {
        fetchUserToken()
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            getUserDetailsUseCase(_userToken.value).collect { user ->
                _userDetails.value = user
            }
        }
    }

    /** Fetches the user token and initializes pagers. */
    private fun fetchUserToken() {
        viewModelScope.launch {
            getUserTokenUseCase().collect { token ->
                _userToken.value = token
                getUserDetails()
                // Initialize Pagers once token is available
                initializePagers(token)
            }
        }
    }

    /** Initializes pagers with the fetched token. */
    private fun initializePagers(token: String) {
        _authorPage.value = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { AuthorPageSource(authorRepo, token) }
        ).flow.cachedIn(viewModelScope)

        _blogPage.value = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { BlogPageSource(blogRepo, token) }
        ).flow.cachedIn(viewModelScope)
    }
}