package com.dicoding.kenalikan.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dicoding.kenalikan.data.weatherclass.WeatherTools
import com.dicoding.kenalikan.retrofit.ApiWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherTools?>()
    val weatherData: MutableLiveData<WeatherTools?>
        get() = _weatherData

    val lastSearchedCityNameKey = "lastSearchedCityName"

    fun fetchWeatherData(cityName: String) {
        val apiService = ApiWeather.getApiService()
        val response = apiService.getWeatherData(cityName, "89efb9d28d18dc6a9d8c64a9d214cc59", "metric")
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
                // Handle the error appropriately, e.g., show a Toast or log the error
            }
        })
    }
}
