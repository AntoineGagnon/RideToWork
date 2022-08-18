package com.example.android.wearable.composestarter.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherData(val daily: List<DailyWeather>)

@Serializable
class DailyWeather(val dt: Long, val temp: Temperature, val rain: Float = 0f, val snow: Float= 0f)

@Serializable
class Temperature(val min: Float, val max: Float)
