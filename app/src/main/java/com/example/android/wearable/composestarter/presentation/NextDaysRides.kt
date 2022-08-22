package com.example.android.wearable.composestarter.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.*
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.android.wearable.composestarter.presentation.theme.WearAppTheme
import com.example.android.wearable.composestarter.presentation.ui.WeatherAnimation
import com.example.android.wearable.composestarter.presentation.utils.statusColor

@Composable
fun NextDaysRides(
    navigationController: NavController,
    weatherViewModel: WeatherViewModel = viewModel()
) {
    val nextDaysWeather by weatherViewModel.nextDaysWeather.collectAsState(emptyList())
    WearAppTheme {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .selectableGroup(),
            verticalArrangement = Arrangement.Center
        ) {
            items(nextDaysWeather) { weatherInfo ->
                WeatherChip(weatherInfo)
            }
        }
    }
}

@Composable
private fun WeatherChip(
    weatherInfo: WeatherInfo
) {
    val day = weatherInfo.day
    Chip(
        label = {
            Text("${day?.name.orEmpty()} ${day?.number ?: ""}")
        },
        secondaryLabel = {
            Text("${weatherInfo.temperature}°C")
        },
        colors = ChipDefaults.imageBackgroundChipColors(
            backgroundImagePainter = ColorPainter(weatherInfo.statusColor())
        ),
        icon = {
            if (weatherInfo.weatherIssue != WeatherIssue.NONE) {
                Box(
                    Modifier
                        .size(ChipDefaults.LargeIconSize)
                        .clip(CircleShape)
                        .border(0.5.dp, Color.White, CircleShape)
                ) {
                    WeatherAnimation(weatherInfo.weatherIssue)
                }
            }
        },
        onClick = {
            // TODO Chips to Cards
        },
        modifier = Modifier.padding(top = 8.dp)
    )
}


@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true, showBackground = true)
@Composable
fun NextDayRidesPreview() {
    NextDaysRides(navigationController = rememberSwipeDismissableNavController())
}
