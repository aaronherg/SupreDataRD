package com.aarondeveloper.supredatard.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.aarondeveloper.dealerpos.ui.theme.MarronEnd
import org.aarondeveloper.dealerpos.ui.theme.MoradoStart

@Composable
fun Cargando() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent.copy(alpha = 0.7f))
            .pointerInput(Unit) {
                detectTapGestures { }
            }
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        val progress = remember { Animatable(0f) }

        LaunchedEffect(progress) {
            progress.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 2000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        }

        val animatedColor by animateColorAsState(
            targetValue = if (progress.value < 0.5f) MoradoStart else MarronEnd,
            animationSpec = tween(durationMillis = 800, easing = LinearEasing)
        )

        CircularProgressIndicator(
            modifier = Modifier.size(120.dp)
                .background(Color.Transparent),
            strokeWidth = 8.dp,
            color = animatedColor,
        )
    }
}
