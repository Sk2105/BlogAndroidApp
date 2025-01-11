package com.composeapp.blogapp.presentation.update_blog


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavHostController
import com.composeapp.blogapp.presentation.common.BackTopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateBlogScreen(navigator:NavHostController) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    var isLoading by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            BackTopBar(scrollBehavior = scrollBehavior){
                navigator.popBackStack()
            }
        },
        containerColor = Color("#212121".toColorInt())
    ) { it ->
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Update Blog",
                fontSize = 40.sp,
                color = Color.White.copy(0.8f),
                modifier = Modifier,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.padding(8.dp)
            )

            OutlinedTextField(
                value = title, onValueChange = { text ->
                    title = text
                },
                supportingText = {
                    Text(
                        text = "${title.length} / 50",
                        color = Color.White.copy(0.8f)
                    )
                },
                textStyle = TextStyle(
                    fontSize = 18.sp
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                label = {
                    Text(text = "Title",
                        fontSize = 18.sp)

                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedLabelColor = Color.White.copy(0.8f),
                    unfocusedLabelColor = Color.White.copy(0.8f),
                    focusedTextColor = Color.White.copy(0.8f),
                    unfocusedTextColor = Color.White.copy(0.8f),
                    focusedBorderColor = Color.White.copy(0.8f),
                    unfocusedBorderColor = Color.White.copy(0.8f)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = content, onValueChange = { text ->
                    content = text
                },
                supportingText = {
                    Text(
                        text = "${content.length} / 5000",
                        color = Color.White.copy(0.8f)
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                label = {
                    Text(text = "Content",
                        fontSize = 18.sp)
                },
                minLines = 5,
                textStyle = TextStyle(
                    fontSize = 18.sp
                ),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedLabelColor = Color.White.copy(0.8f),
                    unfocusedLabelColor = Color.White.copy(0.8f),
                    focusedTextColor = Color.White.copy(0.8f),
                    unfocusedTextColor = Color.White.copy(0.8f),
                    focusedBorderColor = Color.White.copy(0.8f),
                    unfocusedBorderColor = Color.White.copy(0.8f)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    isLoading = !isLoading
                },
                enabled = !isLoading,
                modifier = Modifier
                    .width(200.dp)
                    .align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffffc107),
                    contentColor = Color.Black
                )
            ) {

                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color(0xffffc107),
                        modifier = Modifier
                            .padding(8.dp)
                            .size(20.dp)
                    )
                } else {
                    Text(
                        text = "Update",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }

            }


        }
    }
}