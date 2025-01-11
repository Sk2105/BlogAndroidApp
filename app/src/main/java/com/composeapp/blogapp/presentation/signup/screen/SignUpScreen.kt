package com.composeapp.blogapp.presentation.signup.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.composeapp.blogapp.data.models.RegisterModel
import com.composeapp.blogapp.presentation.common.BackTopBar
import com.composeapp.blogapp.presentation.signup.SignUpViewModel
import com.composeapp.blogapp.presentation.signup.state.RegisterState
import com.composeapp.blogapp.presentation.signup.state.RegisterStateHolder


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navigator: NavHostController
) {
    val context = LocalContext.current

    val viewModel: SignUpViewModel = hiltViewModel()
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var usernameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    val registerState =
        viewModel.registerState.collectAsState(RegisterStateHolder()).value



    LaunchedEffect(key1 = registerState) {
        when (registerState.registerState) {
            is RegisterState.Error -> {
                val error = (registerState.registerState as RegisterState.Error).message
                Toast
                    .makeText(
                        context,
                        "Error $error",
                        Toast.LENGTH_SHORT
                    ).show()
                isLoading = false

            }

            RegisterState.Loading -> {
                isLoading = true

            }

            RegisterState.Success -> {

                Toast.makeText(
                    context,
                    "Sign up successful",
                    Toast.LENGTH_SHORT
                ).show()
                navigator.popBackStack()
            }

            RegisterState.Nothing -> {

            }
        }


    }


    Scaffold(
        modifier = Modifier
            .background(Color(0xff212121))
            .fillMaxSize(),
        topBar = {
            BackTopBar(scrollBehavior = scrollBehavior, onBackPress = {
                navigator.popBackStack()
            })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color(0xff212121))
                .clip(RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Create an account",
                fontSize = 50.sp,
                maxLines = 2,
                lineHeight = TextUnit(
                    50f,
                    TextUnitType.Sp
                ),
                color = Color.White,
                textAlign = TextAlign.Start,
                minLines = 2,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 8.dp)
            )
            Text(
                text = "Sign up to start using the app",
                fontSize = 18.sp,
                color = Color.White.copy(0.5f),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { username = it },
                label = { Text("Username") },
                isError = usernameError.isNotEmpty(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    errorLabelColor = Color.Red,
                    errorCursorColor = Color.Red,
                    errorBorderColor = Color.Red,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Username"
                    )
                }
            )
            if (usernameError.isNotEmpty()) {
                Text(usernameError, color = Color.Gray, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

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
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    errorLabelColor = Color.Red,
                    errorCursorColor = Color.Red,
                    errorBorderColor = Color.Red,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email"
                    )
                },
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
            if (emailError.isNotEmpty()) {
                Text(emailError, color = Color.Gray, fontSize = 12.sp)
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
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    errorLabelColor = Color.Red,
                    errorCursorColor = Color.Red,
                    errorBorderColor = Color.Red,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password"
                    )
                },
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
            )
            if (passwordError.isNotEmpty()) {
                Text(passwordError, color = Color.Gray, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = confirmPassword,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                isError = confirmPasswordError.isNotEmpty(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    errorLabelColor = Color.Red,
                    errorCursorColor = Color.Red,
                    errorBorderColor = Color.Red,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Confirm Password"
                    )
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
            if (confirmPasswordError.isNotEmpty()) {
                Text(confirmPasswordError, color = Color.Gray, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it }
                )

                Text(
                    text = AnnotatedString("Accept ") + AnnotatedString(
                        "Terms & Conditions",
                        spanStyle = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = Color(0xFFffc107)
                        )
                    ), color = Color.White, modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {
                        navigator.popBackStack()

                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        "Sign in",
                        color = Color.White,
                        modifier = Modifier.padding(8.dp),
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        viewModel.register(
                            RegisterModel(
                                name = username,
                                email = email,
                                password = password
                            )
                        )
                    },
                    enabled = !isLoading,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFffc107),
                        contentColor = Color.Black
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(20.dp),
                            color = Color(0xffffc107)
                        )

                    } else {
                        Text("Sign up", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }


}