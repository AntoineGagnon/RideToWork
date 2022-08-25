package com.example.android.wearable.composestarter.presentation.utils

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable

@OptIn(ExperimentalWearMaterialApi::class)
fun Modifier.onSwipeDown(action: () -> Unit): Modifier = composed {
    val swipeableState = rememberSwipeableState(0)
    val anchors = mapOf(0f to 0, 1f to 1)
    if (swipeableState.offset.value < -0.1) {
        action()
    }
    this.swipeable(
        state = swipeableState,
        anchors = anchors,
        thresholds = { _, _ -> FractionalThreshold(0.3f) },
        orientation = Orientation.Vertical
    )
}

@OptIn(ExperimentalWearMaterialApi::class)
fun Modifier.onSwipeUp(action: () -> Unit): Modifier = composed {
    val swipeableState = rememberSwipeableState(0)
    val anchors = mapOf(0f to 0, 1f to 1)
    if (swipeableState.offset.value > 0.1) {
        action()
    }
    this.swipeable(
        state = swipeableState,
        anchors = anchors,
        thresholds = { _, _ -> FractionalThreshold(0.3f) },
        orientation = Orientation.Vertical
    )
}

val Color.Companion.DrabGreen: Color
    get() = Color(0xFF749551)

val Color.Companion.RustRed: Color
    get() = Color(0xFFb7410e)
val Color.Companion.DarkCyan: Color
    get() = Color(0xFF2aa5a5)
