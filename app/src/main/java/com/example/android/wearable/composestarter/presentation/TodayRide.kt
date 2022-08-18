package com.example.android.wearable.composestarter.presentation

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
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
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.android.wearable.composestarter.presentation.theme.WearAppTheme
import com.example.android.wearable.composestarter.presentation.utils.DarkCyan
import com.example.android.wearable.composestarter.presentation.utils.DrabGreen
import com.example.android.wearable.composestarter.presentation.utils.RustRed
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay


@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun TodayRide(navController: NavController, weatherViewModel: WeatherViewModel = viewModel()) {
    val rideState by weatherViewModel.canRideToWork.collectAsState(initial = CanRide.MAYBE)
    val currentPage by weatherViewModel.currentStatus.collectAsState(initial = WeatherViewModel.Status.LOADING)
    val weatherIssue by weatherViewModel.weatherIssue.collectAsState(initial = WeatherIssue.NONE)
    WearAppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            when (currentPage) {
                WeatherViewModel.Status.LOADING ->
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        indicatorColor = MaterialTheme.colors.secondary,
                        trackColor = MaterialTheme.colors.onBackground.copy(alpha = 0.1f),
                        strokeWidth = 4.dp
                    )
                WeatherViewModel.Status.DONE -> WeatherIndicator(rideState, weatherIssue)
            }
        }
    }
}

@Composable
fun WeatherIndicator(rideState: CanRide, weatherIssue: WeatherIssue) {
    val backgroundColor: Color = when (rideState) {
        CanRide.YES -> Color.DrabGreen
        CanRide.NO -> Color.RustRed
        CanRide.MAYBE -> Color.DarkCyan
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {
        if (weatherIssue != WeatherIssue.NONE) {
            WeatherAnimation(weatherIssue)
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
            color = Color.White,
            text = rideState.toString(),
            fontFamily = FontFamily.Monospace,
            fontSize = 30.sp
        )
    }

}

@Composable
fun WeatherAnimation(weatherIssue: WeatherIssue) {
    val configuration = LocalConfiguration.current
    val numberOfDrops = 500
    val screenWidth = configuration.screenWidthDp.toFloat()
    repeat(numberOfDrops) {
        Drop(  Random.nextFloat() * screenWidth, delay = Random.nextLong(until = 5000) )
    }
}

@Composable
fun Drop(x: Float, delay: Long) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    var isDropVisible by remember { mutableStateOf(false)}
    val height = remember { Animatable(0f) }
    LaunchedEffect(height) {
        delay(delay)
        isDropVisible = true
        height.animateTo(screenHeight.toFloat(), animationSpec = tween(1000, easing = EaseIn))
    }
    if(isDropVisible){
        Box(
            modifier = Modifier
                .size(3.dp, 7.dp)
                .offset(x.dp, height.value.dp)
                .background(color = Color(0xFF3b4e70))
        )
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true, showBackground = true)
@Composable
private fun DefaultPreview() {
    WeatherIndicator(rideState = CanRide.MAYBE, weatherIssue = WeatherIssue.RAIN)
}
