package com.composeapp.blogapp.presentation.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.composeapp.blogapp.data.models.User
import com.composeapp.blogapp.presentation.common.SimmerLoader


@Composable
fun AuthorSection(list: List<User>, onClick: (String) -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        ) {
            list.forEach { author ->
                item {
                    AuthorBox(author, onClick)
                }
            }
        }

    }

}

@Composable
fun AuthorBox(author: User, onClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick(author.id) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = author.imageUrl,
            contentDescription = "",
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(500.dp))
                .size(100.dp)
                .border(2.dp, Color(0xFFffc107), RoundedCornerShape(100.dp)),
            contentScale = ContentScale.FillBounds,
        )


        Text(
            text = author.name,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.White
        )


    }
}