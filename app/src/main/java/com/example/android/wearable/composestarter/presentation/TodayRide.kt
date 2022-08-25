package com.example.android.wearable.composestarter.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.foundation.CurvedLayout
import androidx.wear.compose.foundation.CurvedTextStyle
import androidx.wear.compose.foundation.curvedRow
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.curvedText
import com.example.android.wearable.composestarter.presentation.ui.WeatherAnimation
import com.example.android.wearable.composestarter.presentation.utils.onSwipeDown
import com.example.android.wearable.composestarter.presentation.utils.statusColor

@Composable
fun TodayRide(navController: NavController, weatherViewModel: WeatherViewModel = viewModel()) {
    val todayWeather: DataState<WeatherInfo> by weatherViewModel.todayWeather.collectAsState(
        initial = DataState.Loading()
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .onSwipeDown {
                navController.navigate(NavRoute.NEXT_DAYS_RIDE)
            }
    ) {
        if (todayWeather is DataState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                indicatorColor = MaterialTheme.colors.secondary,
                trackColor = MaterialTheme.colors.onBackground.copy(alpha = 0.1f),
                strokeWidth = 4.dp
            )
        } else {
            WeatherIndicator((todayWeather as DataState.Data).data)
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
            WeatherAnimation(
                modifier = Modifier.fillMaxSize(),
                weatherIssue = weatherInfo.weatherIssue
            )
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
        TopText(weatherInfo.day?.name.orEmpty())
    }
}

@Composable
fun TopText(text: String) {
    if (!LocalConfiguration.current.isScreenRound) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text)
        }
    } else {
        CurvedLayout {
            curvedRow {
                curvedText(
                    text = text,
                    style = CurvedTextStyle()
                )
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true, showBackground = true)
@Composable
private fun DefaultPreview() {
    WeatherIndicator(WeatherInfo(null, CanRide.MAYBE, WeatherIssue.RAIN, 10))
}
