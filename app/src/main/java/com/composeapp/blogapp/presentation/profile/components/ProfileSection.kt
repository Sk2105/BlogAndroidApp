package com.composeapp.blogapp.presentation.profile.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.composeapp.blogapp.data.models.User
import com.composeapp.blogapp.presentation.viewmodel.MainViewModel


@Composable
fun ProfileSection(
   user:User
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = user.imageUrl ?: "https://plus.unsplash.com/premium_photo-1689568126014-06fea9d5d341?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            contentDescription = "",
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(500.dp))
                .size(100.dp)
                .shadow(1.dp, ambientColor = Color.White, spotColor = Color.White)
                .border(2.dp, Color(0xFF0066EE), RoundedCornerShape(100.dp)),
            contentScale = ContentScale.FillBounds
        )
        Spacer(
            modifier = Modifier.width(8.dp)
        )

        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = user.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold

            )
            Text(
                text = user.email,
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                color = Color.White.copy(0.6f)

            )
        }

    }
}