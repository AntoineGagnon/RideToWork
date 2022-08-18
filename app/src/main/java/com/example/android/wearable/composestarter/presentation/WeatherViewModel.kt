package com.example.android.wearable.composestarter.presentation

import androidx.lifecycle.ViewModel
import com.mirego.trikot.streams.reactive.BehaviorSubject
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.just
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.reactivestreams.Publisher

class WeatherViewModel: ViewModel() {
enum class Status {
    LOADING,
    DONE
}
    val currentStatus: Flow<Status> = flow {
        emit(Status.LOADING)
        delay(500.milliseconds)
        emit(Status.DONE)
    }
    val canRideToWork: Flow<CanRide> = flowOf(CanRide.NO)
    val weatherIssue: Flow<WeatherIssue> = flowOf(WeatherIssue.RAIN)
}

enum class CanRide {
    YES,
    NO,
    MAYBE
}

enum class WeatherIssue {
    RAIN,
    SNOW,
    NONE
}
