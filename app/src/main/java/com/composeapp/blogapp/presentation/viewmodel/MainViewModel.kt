package com.composeapp.blogapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composeapp.blogapp.data.models.LoginModel
import com.composeapp.blogapp.domain.usecases.auth.LoginUserUseCase
import com.composeapp.blogapp.domain.usecases.local.GetUserTokenUseCase
import com.composeapp.blogapp.domain.usecases.local.SaveUserTokenUseCase
import com.composeapp.blogapp.presentation.viewmodel.state.LoginState
import com.composeapp.blogapp.presentation.viewmodel.state.LoginStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val saveUserTokenUseCase: SaveUserTokenUseCase,
) : ViewModel() {

    var userToken = mutableStateOf("")


    private val _userLoginState =
        MutableStateFlow(LoginStateHolder(LoginState.LoginLoading))
    val userLoginState = _userLoginState.asStateFlow()









    fun loginUser(loginModel: LoginModel) = viewModelScope.launch {
        loginUserUseCase(loginModel)
            .catch {
                _userLoginState.value =
                    LoginStateHolder(LoginState.LoginError(it.message.toString()))
            }.collect { token ->
                run {
                    _userLoginState.value = LoginStateHolder(LoginState.LoginSuccess(token.token))
                    saveUserToken(token.token)
                }
            }
    }

    fun getUserToken() =
        getUserTokenUseCase()


    private fun saveUserToken(token: String) = viewModelScope.launch { saveUserTokenUseCase(token) }


}