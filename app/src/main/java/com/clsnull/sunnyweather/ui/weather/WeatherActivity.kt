package com.clsnull.sunnyweather.ui.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.clsnull.sunnyweather.R
import com.clsnull.sunnyweather.databinding.ActivityWeatherBinding
import com.clsnull.sunnyweather.logic.model.Weather
import com.clsnull.sunnyweather.logic.model.WeatherViewModel

class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherBinding

    val viewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }

        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }

        viewModel.weatherLiveData.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }

    private fun showWeatherInfo(weather: Weather) {
        binding.nowIncloud.placeName.text = viewModel.placeName
    }
}