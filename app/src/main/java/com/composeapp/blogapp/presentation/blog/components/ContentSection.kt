package com.composeapp.blogapp.presentation.blog.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.composeapp.blogapp.data.models.BlogAuthorModel


@Composable
fun ContentSection(
    blogAuthorModel: BlogAuthorModel
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))

    ) {


        MarkdownViewer(
            modifier = Modifier.fillMaxWidth(),
            content = blogAuthorModel.content
        )

    }
}