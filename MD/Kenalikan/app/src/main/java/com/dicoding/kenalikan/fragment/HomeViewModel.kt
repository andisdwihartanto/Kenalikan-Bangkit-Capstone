package com.dicoding.kenalikan.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dicoding.kenalikan.retrofit.ApiService
import com.dicoding.kenalikan.weatherclass.WeatherTools
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel(val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherTools?>()
    val weatherData: MutableLiveData<WeatherTools?>
        get() = _weatherData

    val lastSearchedCityNameKey = "lastSearchedCityName"

    fun fetchWeatherData(cityName: String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiService::class.java)
        val response = retrofit.getWeatherData(cityName, "89efb9d28d18dc6a9d8c64a9d214cc59", "metric")
        response.enqueue(object : Callback<WeatherTools> {
            override fun onResponse(call: Call<WeatherTools>, response: Response<WeatherTools>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _weatherData.value = responseBody
                    // Simpan nama kota yang baru dicari ke savedStateHandle
                    savedStateHandle[lastSearchedCityNameKey] = cityName
                }
            }

            override fun onFailure(call: Call<WeatherTools>, t: Throwable) {
                val errorMessage = "Failed to fetch weather data. Please check your internet connection and try again later."
//            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}