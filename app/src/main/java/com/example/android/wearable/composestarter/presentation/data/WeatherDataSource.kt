package com.example.android.wearable.composestarter.presentation.data

import com.example.android.wearable.composestarter.presentation.model.WeatherData
import com.example.android.wearable.composestarter.presentation.utils.httpPromise
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.promise.Promise
import io.ktor.client.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

class WeatherDataSource {

    private val client = HttpClient {
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
            install(HttpCache)

        }
    }

    fun getWeatherData(): Promise<WeatherData> {
        return httpPromise(CancellableManager(), CoroutineScope(Dispatchers.IO)) {
            delay(2.seconds)
            client.get("https://api.openweathermap.org/data/3.0/onecall?lat=45.438980&lon=-73.678770&units=metric&exclude=current,minutely,hourly,alerts&appid=${APIConfig.WEATHER_API_KEY}")
        }
    }
}
