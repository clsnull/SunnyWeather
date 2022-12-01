package com.clsnull.sunnyweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.clsnull.sunnyweather.databinding.ActivityMainBinding
import com.clsnull.sunnyweather.ui.place.PlaceViewModel

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)
        binding.hello.setOnClickListener{
            placeViewModel.searchPlaces("贵港 港南区")
        }

        placeViewModel.placeListData.observe(this, Observer {
            Log.d("TAG", "onCreate: 监听到数据变化了")
            if (it.isSuccess) {
                val placeList = it.getOrNull()
                placeList?.apply {
                    for (place in this) {
                        Log.d(TAG, "onCreate: $place")
                    }
                }
            } else {
                Log.d(TAG, "error onCreate: $it")
            }
        })
    }
}