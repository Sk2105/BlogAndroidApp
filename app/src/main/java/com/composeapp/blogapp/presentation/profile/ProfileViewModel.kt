package com.composeapp.blogapp.presentation.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.composeapp.blogapp.data.models.BlogAuthorModel
import com.composeapp.blogapp.data.models.User
import com.composeapp.blogapp.data.source.BlogPageSource
import com.composeapp.blogapp.domain.repo.AuthorRepo
import com.composeapp.blogapp.domain.usecases.auth.GetUserDetailsUseCase
import com.composeapp.blogapp.domain.usecases.auth.UploadProfileUseCase
import com.composeapp.blogapp.domain.usecases.author.GetAuthorByIdUseCase
import com.composeapp.blogapp.domain.usecases.author.GetBlogsByAuthorIdUseCase
import com.composeapp.blogapp.domain.usecases.local.GetUserTokenUseCase
import com.composeapp.blogapp.domain.usecases.local.SaveUserTokenUseCase
import com.composeapp.blogapp.presentation.profile.state.AuthorState
import com.composeapp.blogapp.presentation.profile.state.AuthorStateHolder
import com.composeapp.blogapp.presentation.profile.state.UploadState
import com.composeapp.blogapp.presentation.profile.state.UploadStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel
@Inject constructor(
    private val authorRepo: AuthorRepo,
    private val getAuthorByIdUseCase: GetAuthorByIdUseCase,
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val uploadProfileUseCase: UploadProfileUseCase,
    private val saveUserTokenUseCase: SaveUserTokenUseCase
) : ViewModel() {

    private val _userToken = mutableStateOf("")

    private val _blogPage = MutableStateFlow(emptyList<PagingData<BlogAuthorModel>>().asFlow())
    val blogPage = _blogPage
    private val _userDetails = mutableStateOf<User?>(null)
    val userDetails = _userDetails

    private val _author = MutableStateFlow(AuthorStateHolder())
    val author = _author.asStateFlow()

    private val _uploadingStatus = Channel<UploadStateHolder>()
    val uploadingStatus = _uploadingStatus.receiveAsFlow()

    init {
        fetchUserToken()
    }

    private fun fetchUserToken() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserTokenUseCase().collect {
                _userToken.value = it
                getUserDetails()
            }
        }
    }

    private fun getUserDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserDetailsUseCase(token = _userToken.value).catch {
                _userDetails.value = null
            }.collect {
                _userDetails.value = it
            }
        }
    }

    private fun fetchBlogsByAuthorId(id: String) {
        _blogPage.value = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                BlogPageSource(
                    authorRepo = authorRepo,
                    token = _userToken.value,
                    authorId = id
                )
            }
        ).flow.cachedIn(viewModelScope)

    }


    fun uploadFile(file: MultipartBody.Part) = viewModelScope.launch {
        _uploadingStatus.send(UploadStateHolder(UploadState.Progress))
        getUserTokenUseCase().collect { token ->
            uploadProfileUseCase(token, file).catch {
                _uploadingStatus.send(UploadStateHolder(UploadState.Error(it.message.toString())))
            }.collect {
                _uploadingStatus.send(UploadStateHolder(UploadState.Success))
            }
        }
    }

    fun fetchAuthorByIdDetails(authorId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getAuthorByIdUseCase(id = authorId, token = _userToken.value).catch {
                _author.value = AuthorStateHolder(AuthorState.Error(it.message.toString()))
            }.collect {
                _author.value = AuthorStateHolder(AuthorState.Success(it))
                fetchBlogsByAuthorId(authorId)
            }
        }

    }

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            saveUserTokenUseCase("")
        }
    }


}