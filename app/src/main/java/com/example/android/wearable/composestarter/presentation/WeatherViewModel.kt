package com.example.android.wearable.composestarter.presentation

import androidx.lifecycle.ViewModel
import java.time.DayOfWeek
import java.util.*
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class WeatherViewModel : ViewModel() {
    private val random = Random(System.currentTimeMillis())

    enum class Status {
        LOADING,
        DONE
    }

    val dataLoadingStatus: Flow<Status> = flow {
        emit(Status.LOADING)
        delay(1000)
        emit(Status.DONE)
    }

    val nextDaysWeather: Flow<List<WeatherInfo>> = flow {
        val days = DayOfWeek.values().toList()
        Collections.rotate(days, 2)
        emit(
                days
                    .mapIndexed { index, dayOfWeek ->
                        val weatherIssue = listOf(
                            WeatherIssue.RAIN,
                            WeatherIssue.SNOW,
                            WeatherIssue.RAIN,
                            WeatherIssue.SNOW,
                            WeatherIssue.NONE
                        ).random(random)
                        val temperature = if (weatherIssue == WeatherIssue.SNOW) {
                            Random.nextInt(10, 25)
                        } else {
                            Random.nextInt(-10, 5)
                        }
                            WeatherInfo(
                                day = Day(
                                    dayOfWeek.name.lowercase().capitalize(Locale.CANADA),
                                    24 + index
                                ),
                                canRide = listOf(
                                    CanRide.NO,
                                    CanRide.NO,
                                    CanRide.YES
                                ).random(random),
                                weatherIssue = weatherIssue,
                                temperature = temperature
                            )
                    }
        )
    }

    val todayWeather: Flow<WeatherInfo> =
        flowOf(WeatherInfo(Day("Thursday", 24), CanRide.NO, WeatherIssue.SNOW, -5))
}

data class Day(
    val name: String,
    val number: Int
)

data class WeatherInfo(
    val day: Day? = null,
    val canRide: CanRide,
    val weatherIssue: WeatherIssue,
    val temperature: Int
)

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
