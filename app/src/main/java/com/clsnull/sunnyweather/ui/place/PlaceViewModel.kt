package com.clsnull.sunnyweather.ui.place

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.clsnull.sunnyweather.logic.Repository
import com.clsnull.sunnyweather.logic.model.Place

class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeListData = Transformations.switchMap(searchLiveData) { query ->
        val searchPlaces = Repository.searchPlaces(query)
        val listResult = searchPlaces.value
        Log.d("PlaceViewModel", "listResult: $searchPlaces")
        searchPlaces
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}