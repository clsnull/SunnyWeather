package com.clsnull.sunnyweather.logic

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.clsnull.sunnyweather.logic.model.Place
import com.clsnull.sunnyweather.logic.model.Weather
import com.clsnull.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object Repository {

    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val searchPlaces = SunnyWeatherNetwork.searchPlaces(query)
        if (searchPlaces.status == "ok") {
            val places = searchPlaces.places
            Result.success(places)
        } else {
            Result.failure(java.lang.RuntimeException("response status is ${searchPlaces.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather =
                    Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(java.lang.RuntimeException("realtime response status is ${realtimeResponse.status} daily response status is ${dailyResponse.status}"))
            }
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>> {
            val result = try {
                block()
            } catch (e: java.lang.Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}