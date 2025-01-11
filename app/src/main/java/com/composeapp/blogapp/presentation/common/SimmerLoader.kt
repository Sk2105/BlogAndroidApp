package com.composeapp.blogapp.presentation.common

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun SimmerLoader(
    modifier: Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(
        label = "SimmerEffect"
    )
    val colorEffect = infiniteTransition.animateColor(
        initialValue = Color.Gray.copy(0.1f),
        targetValue = Color.Gray.copy(0.2f),
        animationSpec = InfiniteRepeatableSpec(tween(1000), repeatMode = RepeatMode.Reverse),
        label = "color effect"
    )

    Box(modifier = modifier.background(colorEffect.value))
}