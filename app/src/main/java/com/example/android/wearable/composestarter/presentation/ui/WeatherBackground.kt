package com.example.android.wearable.composestarter.presentation.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.wear.compose.material.Icon
import com.example.android.wearable.composestarter.R
import com.example.android.wearable.composestarter.presentation.WeatherIssue
import kotlin.random.Random

@Composable
fun WeatherAnimation(modifier: Modifier = Modifier, weatherIssue: WeatherIssue) {
    when (weatherIssue) {
        WeatherIssue.RAIN -> {
            FallingImageBackground(
                numberOfDrops = 30,
                imageId = R.drawable.ic_rain,
                color = Color(0xFF4D8CFF),
                modifier = modifier
            )
        }
        WeatherIssue.SNOW -> {
            FallingImageBackground(
                numberOfDrops = 20,
                imageId = R.drawable.ic_snow,
                color = Color.White,
                modifier = modifier
            )
        }
        WeatherIssue.NONE -> {
            RotatingIcon(
                imageId = R.drawable.ic_sun,
                color = Color.Yellow,
                modifier = modifier
            )
        }
    }
}

@Composable
fun RotatingIcon(@DrawableRes imageId: Int, color: Color, modifier: Modifier) {
    val rotation by rememberInfiniteTransition()
        .animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(4000, easing = LinearEasing)
            )
        )
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Icon(
            modifier = Modifier
                .fillMaxSize(0.60f)
                .rotate(rotation),
            contentDescription = "Rotating sun icon",
            imageVector = ImageVector.vectorResource(id = imageId),
            tint = color
        )
    }
}

@Composable
fun FallingImageBackground(numberOfDrops: Int, imageId: Int, color: Color, modifier: Modifier) {
    val vector = ImageVector.vectorResource(id = imageId)
    val painter = rememberVectorPainter(image = vector)
    val heightAnimations: List<State<Float>> = buildList {
        repeat(numberOfDrops) {
            add(
                rememberInfiniteTransition().animateFloat(
                    initialValue = -0.10f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = EaseIn),
                        repeatMode = RepeatMode.Restart,
                        initialStartOffset = StartOffset(Random.nextInt(until = 1000))
                    )
                )
            )
        }
    }
    val xs: List<Float> = List(numberOfDrops) { Random.nextFloat() }
    Canvas(modifier = modifier.fillMaxSize(), onDraw = {
        repeat(numberOfDrops) { index ->
            drawFallingImage(
                x = xs[index] * size.width,
                painter = painter,
                color = color,
                heightAnimation = heightAnimations[index]
            )
        }
    })
}

fun DrawScope.drawFallingImage(
    x: Float,
    painter: Painter,
    color: Color,
    heightAnimation: State<Float>
) {
    val iconSize = (size.width / 8)
    translate(x, heightAnimation.value * size.height) {
        with(painter) {
            draw(Size(iconSize, iconSize), colorFilter = ColorFilter.tint(color))
        }
    }
}
