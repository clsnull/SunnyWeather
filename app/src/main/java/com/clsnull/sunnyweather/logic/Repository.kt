package com.clsnull.sunnyweather.logic

import androidx.lifecycle.liveData
import com.clsnull.sunnyweather.logic.model.Place
import com.clsnull.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

object Repository {

    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val searchPlaces = SunnyWeatherNetwork.searchPlaces(query)
            if (searchPlaces.status == "ok") {
                val places = searchPlaces.places
                Result.success(places)
            } else {
                Result.failure(java.lang.RuntimeException("response status is ${searchPlaces.status}"))
            }
        } catch (e: java.lang.Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }


}