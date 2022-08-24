package com.example.android.wearable.composestarter.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.*
import com.example.android.wearable.composestarter.R
import com.example.android.wearable.composestarter.presentation.ui.WeatherAnimation
import com.example.android.wearable.composestarter.presentation.utils.statusColor

@Composable
fun NextDaysRides(weatherViewModel: WeatherViewModel = viewModel()) {
    val nextDaysWeather by weatherViewModel.nextDaysWeather.collectAsState(emptyList())
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center
    ) {
        items(nextDaysWeather) { weatherInfo ->
            WeatherChip(weatherInfo)
        }
    }
}

@Composable
private fun WeatherChip(weatherInfo: WeatherInfo, animated: Boolean = true) {
    val day = weatherInfo.day
    Chip(
        label = { Text("${day?.name.orEmpty()} ${day?.number ?: ""}") },
        secondaryLabel = { Text("${weatherInfo.temperature}°C") },
        colors = ChipDefaults.imageBackgroundChipColors(
            backgroundImagePainter = ColorPainter(weatherInfo.statusColor())
        ),
        icon = {
            val iconModifier = Modifier
                .size(ChipDefaults.LargeIconSize)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = weatherInfo
                        .statusColor()
                        .copy(alpha = 0.4f),
                    shape = CircleShape
                )
            if (!animated) {
                WeatherIcon(
                    modifier = iconModifier,
                    weatherIssue = weatherInfo.weatherIssue
                )
            } else {
                WeatherAnimation(modifier = iconModifier, weatherIssue = weatherInfo.weatherIssue)
            }
        },
        onClick = { },
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
fun WeatherIcon(modifier: Modifier, weatherIssue: WeatherIssue) {
    val image: ImageVector = when (weatherIssue) {
        WeatherIssue.RAIN -> ImageVector.vectorResource(id = R.drawable.ic_rain)
        WeatherIssue.SNOW -> ImageVector.vectorResource(id = R.drawable.ic_snow)
        WeatherIssue.NONE -> ImageVector.vectorResource(id = R.drawable.ic_sun)
    }
    val tint: Color = when (weatherIssue) {
        WeatherIssue.RAIN -> Color(0xFF4D8CFF)
        WeatherIssue.SNOW -> Color.White
        WeatherIssue.NONE -> Color.Yellow
    }
    Icon(
        modifier = modifier
            .padding(4.dp)
            .fillMaxSize(),
        contentDescription = "Weather icon",
        imageVector = image,
        tint = tint
    )
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true, showBackground = true)
@Composable
fun NextDayRidesPreview() {
    NextDaysRides()
}
