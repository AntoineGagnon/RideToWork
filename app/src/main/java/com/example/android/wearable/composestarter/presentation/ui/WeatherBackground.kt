package com.example.android.wearable.composestarter.presentation.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import com.example.android.wearable.composestarter.R
import com.example.android.wearable.composestarter.presentation.WeatherIssue
import kotlin.random.Random
import kotlinx.coroutines.delay


@Composable
fun WeatherAnimation(weatherIssue: WeatherIssue) {
    val configuration = LocalConfiguration.current
    val numberOfDrops = 500
    val screenWidth = configuration.screenWidthDp.toFloat()
    repeat(numberOfDrops) {
        val imageId: Int
        val color: Color
        when (weatherIssue) {
            WeatherIssue.RAIN -> {
                imageId = R.drawable.ic_drop
                color = Color(0xFF3b4e70)
            }
            WeatherIssue.SNOW -> {
                imageId = R.drawable.ic_snow
                color = Color.White
            }
            WeatherIssue.NONE -> {
                imageId = R.drawable.ic_sun
                color = Color(0xFFFFD500)
            }
        }
        FallingImage(
            x = Random.nextFloat() * screenWidth,
            delay = Random.nextLong(until = 5000),
            image = imageId,
            color = color
        )
    }
}


@Composable
fun FallingImage(x: Float, delay: Long, @DrawableRes image: Int, color: Color) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    var isDropVisible by remember { mutableStateOf(false) }
    val height = remember { Animatable(0f) }
    LaunchedEffect(height) {
        delay(delay)
        isDropVisible = true
        height.animateTo(screenHeight.toFloat(), animationSpec = tween(1000, easing = EaseIn))
    }
    if (isDropVisible) {
        Icon(
            painter = painterResource(id = image),
            contentDescription = "",
            modifier = Modifier
                .size(15.dp)
                .offset(x.dp, height.value.dp),
            tint = color

        )
    }
}
