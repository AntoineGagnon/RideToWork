package com.example.android.wearable.composestarter.presentation.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.android.wearable.composestarter.R
import com.example.android.wearable.composestarter.presentation.WeatherIssue
import kotlin.random.Random


@Composable
fun WeatherAnimation(weatherIssue: WeatherIssue) {
    val numberOfDrops = 50
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
            return
        }
    }
    val vector = ImageVector.vectorResource(id = imageId)
    val painter = rememberVectorPainter(image = vector)
    val animatedHeights: List<State<Float>> = buildList {
        repeat(numberOfDrops) {
            add(
                rememberInfiniteTransition().animateFloat(
                    initialValue = -0.10f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        tween(1000, easing = EaseIn),
                        RepeatMode.Restart,
                        initialStartOffset = StartOffset(Random.nextInt(until = 1000))
                    )
                )
            )
        }
    }
    val xs: List<Float> = List(numberOfDrops) { Random.nextFloat() }
    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        repeat(numberOfDrops) { index ->
            drawFallingImage(
                x = xs[index] * size.width,
                painter = painter,
                color = color,
                height = animatedHeights[index]
            )
        }
    })
}

fun DrawScope.drawFallingImage(
    x: Float,
    painter: Painter,
    color: Color,
    height: State<Float>
) {
    val iconSize = (size.width/10)
    translate(x, (height.value * size.height)) {
        with(painter) {
            draw(Size(iconSize,iconSize), colorFilter = ColorFilter.tint(color))
        }
    }
}
