package com.example.android.wearable.composestarter.presentation

import androidx.lifecycle.ViewModel
import java.time.DayOfWeek
import java.util.*
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherViewModel : ViewModel() {
    private val random = Random(System.currentTimeMillis())

    val nextDaysWeather: Flow<List<WeatherInfo>> = flow {
        val days = DayOfWeek.values().toList()
        Collections.rotate(days, 4)
        emit(
            days
                .mapIndexed { index, dayOfWeek ->
                    val weatherIssue = listOf(
                        WeatherIssue.SNOW,
                        WeatherIssue.SNOW,
                        WeatherIssue.RAIN,
                        WeatherIssue.RAIN,
                        WeatherIssue.NONE
                    ).random(random)
                    val temperature = if (weatherIssue == WeatherIssue.SNOW) {
                        Random.nextInt(-20, 5)
                    } else {
                        Random.nextInt(10, 25)
                    }
                    val canRide = if (weatherIssue == WeatherIssue.NONE) {
                        CanRide.YES
                    } else {
                        listOf(CanRide.NO, CanRide.NO, CanRide.MAYBE).random(random)
                    }
                    WeatherInfo(
                        day = Day(
                            dayOfWeek.name.lowercase()
                                .replaceFirstChar { it.titlecase(Locale.CANADA) },
                            24 + index
                        ),
                        canRide = canRide,
                        weatherIssue = weatherIssue,
                        temperature = temperature
                    )
                }
        )
    }

    val todayWeather: Flow<DataState<WeatherInfo>> =
        flow {
            emit(DataState.Loading())
            delay(500)
            emit(
                DataState.Data(
                    WeatherInfo(
                        Day("Wednesday", 24),
                        CanRide.NO,
                        WeatherIssue.RAIN,
                        -5
                    )
                )
            )
        }
}

data class WeatherInfo(
    val day: Day? = null,
    val canRide: CanRide,
    val weatherIssue: WeatherIssue,
    val temperature: Int
)

data class Day(
    val name: String,
    val number: Int
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

sealed class DataState<T> {
    class Loading<T> : DataState<T>()
    data class Data<T>(val data: T) : DataState<T>()
}
