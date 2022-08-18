package com.example.android.wearable.composestarter.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.wear.compose.material.MaterialTheme
import com.example.android.wearable.composestarter.presentation.theme.WearAppTheme
import com.example.android.wearable.composestarter.presentation.utils.onSwipeUp

@Composable
fun NextDaysRides(navigationController: NavController) {
    WearAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .onSwipeUp {
                    navigationController.navigate(NavRoute.TODAY_RIDE) }
                .selectableGroup(),
            verticalArrangement = Arrangement.Center
        ) {
            MainText("NEXT DAY RIDES")
        }
    }
}
