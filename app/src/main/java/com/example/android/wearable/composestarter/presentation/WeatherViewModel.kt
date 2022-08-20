package com.example.android.wearable.composestarter.presentation

import androidx.lifecycle.ViewModel
import java.time.DayOfWeek
import java.util.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class WeatherViewModel : ViewModel() {
    enum class Status {
        LOADING,
        DONE
    }

    val dataLoadingStatus: Flow<Status> = flow {
        emit(Status.LOADING)
        delay(1000)
        emit(Status.DONE)
    }

    val nextDaysWeather: Flow<Map<Day, WeatherInfo>> = flow {
        val days = DayOfWeek.values().toList()
        Collections.rotate(days, 2)
        emit(
            buildMap {
                days
                    .forEachIndexed { index, dayOfWeek ->
                        put(
                            Day(dayOfWeek.name, 24 + index),
                            WeatherInfo(
                                canRide = CanRide.values().random(),
                                weatherIssue = WeatherIssue.values().random()
                            )
                        )
                    }
            }
        )
    }

    val todayWeather: Flow<WeatherInfo> = nextDaysWeather.map {
        it.values.first()
    }
}

data class WeatherInfo(val canRide: CanRide, val weatherIssue: WeatherIssue)
data class Day(val name: String, val number: Int)
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
