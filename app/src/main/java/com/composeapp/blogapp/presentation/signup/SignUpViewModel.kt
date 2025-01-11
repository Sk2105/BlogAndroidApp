package com.composeapp.blogapp.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composeapp.blogapp.data.models.RegisterModel
import com.composeapp.blogapp.data.models.User
import com.composeapp.blogapp.domain.usecases.auth.RegisterUserUseCase
import com.composeapp.blogapp.presentation.signup.state.RegisterState
import com.composeapp.blogapp.presentation.signup.state.RegisterStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private  val registerUserUseCase: RegisterUserUseCase
):ViewModel()
{
    private val registerChannel = Channel<RegisterStateHolder>()
    val registerState = registerChannel.receiveAsFlow()


    fun register(registerModel: RegisterModel)= viewModelScope.launch(Dispatchers.IO){
        registerChannel.send(RegisterStateHolder(RegisterState.Loading))
        registerUserUseCase(registerModel).catch {
            registerChannel.send(RegisterStateHolder(RegisterState.Error(it.message.toString())))
        }.collect{
            registerChannel.send(RegisterStateHolder(RegisterState.Success))
        }

    }
}