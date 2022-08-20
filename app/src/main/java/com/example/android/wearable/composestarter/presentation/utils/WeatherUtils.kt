package com.example.android.wearable.composestarter.presentation.utils

import androidx.compose.ui.graphics.Color
import com.example.android.wearable.composestarter.presentation.CanRide
import com.example.android.wearable.composestarter.presentation.WeatherInfo

fun WeatherInfo.statusColor(): Color {
    return when (canRide) {
        CanRide.YES -> Color.DrabGreen
        CanRide.NO -> Color.RustRed
        CanRide.MAYBE -> Color.DarkCyan
    }
}
