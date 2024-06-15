package com.dicoding.kenalikan.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContextCompat
import com.dicoding.kenalikan.R
import com.dicoding.kenalikan.databinding.FragmentHomeBinding
import com.dicoding.kenalikan.retrofit.ApiService
import com.dicoding.kenalikan.weatherclass.WeatherTools
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchWeatherData("Jakarta")
        searchCity()

        binding.ivProfile.setOnClickListener {
        }
    }
    private fun searchCity() {
        val searchView = binding.svSearch
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    fetchWeatherData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun fetchWeatherData(cityName:String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiService::class.java)
        val response = retrofit.getWeatherData(cityName, "89efb9d28d18dc6a9d8c64a9d214cc59", "metric")
        response.enqueue(object : Callback<WeatherTools> {
            override fun onResponse(call: Call<WeatherTools>, response: Response<WeatherTools>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    val temperature = responseBody.main.temp.toString()
                    val windSpeed = responseBody.wind.speed
                    val seaLevel = responseBody.main.pressure
                    val condition = responseBody.weather.firstOrNull()?.main?: "unknown"


                    binding.tvTemperature.text= "$temperature °C"
                    binding.tvWindspeed.text = "$windSpeed m/s"
                    binding.tvWaves.text = "$seaLevel sPh"
                    binding.tvCondition.text = condition
                    binding.tvCity.text = "$cityName"

                    changeImagesAccordingToWeatherCondition(condition)
                }
            }

            override fun onFailure(call: Call<WeatherTools>, t: Throwable) {
                TODO("Not yet implement ed")
            }
        })
    }
    private fun changeImagesAccordingToWeatherCondition(conditions: String) {

        when (conditions){
            "Clear Sky", "Sunny", "Clear" ->{
                binding.ivWeather.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.svg_sun))
            }
            "Partly Clouds", "Clouds", "Overcast", "Mist", "Foggy" -> {
                binding.ivWeather.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.svg_clouds))
            }
            "Thunderstorm" ,"Rain", "Light Rain", "Drizzle", "Moderate Rain", "Showers", "Heavy Rain" -> {
                binding.ivWeather.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.svg_rain))
            }
            else ->{
                binding.ivWeather.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.svg_sun))
            }

        }
        isRemoving
    }
}