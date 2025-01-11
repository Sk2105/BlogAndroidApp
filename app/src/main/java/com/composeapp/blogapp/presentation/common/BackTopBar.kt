package com.composeapp.blogapp.presentation.common

import androidx.compose.runtime.Composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onBackPress: () -> Unit
) {

    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = { },
        navigationIcon = {
            IconButton(
                onClick = { onBackPress() },
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray.copy(0.2f))
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xff212121),
            scrolledContainerColor = Color(0xff212121),
            navigationIconContentColor = Color.White
        )

    )
}