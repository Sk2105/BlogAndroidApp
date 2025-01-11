package com.composeapp.blogapp.presentation.blog.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TitleSection(
    title:String = ""
)
{
    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)){
        Text(
            text = title,
            color = Color.White,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 50.sp,



        )
    }
}