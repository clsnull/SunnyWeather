package com.clsnull.sunnyweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.clsnull.sunnyweather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}