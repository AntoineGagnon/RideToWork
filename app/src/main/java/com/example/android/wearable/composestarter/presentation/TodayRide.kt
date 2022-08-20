package com.example.android.wearable.composestarter.presentation

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.android.wearable.composestarter.presentation.theme.WearAppTheme
import com.example.android.wearable.composestarter.presentation.ui.WeatherAnimation
import com.example.android.wearable.composestarter.presentation.utils.onSwipeDown
import com.example.android.wearable.composestarter.presentation.utils.statusColor
import kotlinx.coroutines.delay


@Composable
fun TodayRide(navController: NavController, weatherViewModel: WeatherViewModel = viewModel()) {
    val todayWeather: WeatherInfo by weatherViewModel.todayWeather.collectAsState(
        initial = WeatherInfo(
            CanRide.MAYBE,
            WeatherIssue.NONE
        )
    )
    val currentPage by weatherViewModel.dataLoadingStatus.collectAsState(initial = WeatherViewModel.Status.LOADING)
    WearAppTheme {
        Box(modifier = Modifier
            .fillMaxSize()
            .onSwipeDown {
                navController.navigate(NavRoute.NEXT_DAYS_RIDE)
            }) {
            when (currentPage) {
                WeatherViewModel.Status.LOADING ->
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        indicatorColor = MaterialTheme.colors.secondary,
                        trackColor = MaterialTheme.colors.onBackground.copy(alpha = 0.1f),
                        strokeWidth = 4.dp
                    )
                WeatherViewModel.Status.DONE -> WeatherIndicator(todayWeather)
            }
        }
    }
}

@Composable
fun WeatherIndicator(weatherInfo: WeatherInfo) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = weatherInfo.statusColor())
    ) {
        if (weatherInfo.weatherIssue != WeatherIssue.NONE) {
            WeatherAnimation(weatherInfo.weatherIssue)
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
            color = Color.White,
            text = weatherInfo.canRide.toString(),
            fontFamily = FontFamily.Monospace,
            fontSize = 30.sp
        )
    }
}
@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true, showBackground = true)
@Composable
private fun DefaultPreview() {
    WeatherIndicator(WeatherInfo(CanRide.MAYBE, WeatherIssue.RAIN))
}
