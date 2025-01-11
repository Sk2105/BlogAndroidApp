package com.composeapp.blogapp.presentation.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.composeapp.blogapp.data.models.LoginModel
import com.composeapp.blogapp.presentation.navigation.graph.AppGraph
import com.composeapp.blogapp.presentation.viewmodel.MainViewModel
import com.composeapp.blogapp.presentation.viewmodel.state.LoginState
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(navigator: NavHostController,
                viewModel: MainViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    var loginState = viewModel.userLoginState.collectAsState().value


    var context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff212121)),

        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(color =  Color(0xff212121))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Welcome to Blog App",
                fontSize = 60.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                lineHeight = TextUnit(
                    60f,
                    TextUnitType.Sp
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "Sign in to access your blog",
                fontSize = 18.sp,
                color = Color.Gray,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { email = it },
                label = { Text("Email address") },
                isError = emailError.isNotEmpty(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray.copy(0.5f),
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    errorCursorColor = Color.Red,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email"
                    )
                }
            )
            if (emailError.isNotEmpty()) {
                Text(emailError, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                isError = passwordError.isNotEmpty(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray.copy(0.5f),
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    errorCursorColor = Color.Red,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password"
                    )
                }

            )
            if (passwordError.isNotEmpty()) {
                Text(passwordError, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it }
                )
                Text("Remember me", modifier = Modifier.padding(start = 8.dp))
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "Forgot your password?",
                    color = Color.White,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { println("Forgot password clicked") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {
                        navigator.navigate(AppGraph.SignUp)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    )
                ) {
                    Text("Sign up", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        isLoading = true
                        viewModel.loginUser(
                          LoginModel(
                              email = email,
                              password = password
                          )
                        )
                    },
                    enabled = !isLoading,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xffFFC107),
                        contentColor = Color.Black
                    )
                ) {
                    if (isLoading) {
                        when(loginState.loginState){
                            is LoginState.LoginError -> {
                                isLoading = false
                                val error = (loginState.loginState as LoginState.LoginError).error
                                Toast.makeText(
                                    context,
                                    error,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            LoginState.LoginLoading -> {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .size(20.dp),
                                    color = Color(0xffFFC107)
                                )
                            }
                            is LoginState.LoginSuccess -> {
                                isLoading = false
                                Toast.makeText(
                                    context,
                                    "Login successful",
                                    Toast.LENGTH_LONG
                                ).show()
                                navigator.navigate(AppGraph.HomeScreen)
                            }
                        }
                    } else {
                        Text("Sign in", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
                    }
                }


            }
        }

    }


}