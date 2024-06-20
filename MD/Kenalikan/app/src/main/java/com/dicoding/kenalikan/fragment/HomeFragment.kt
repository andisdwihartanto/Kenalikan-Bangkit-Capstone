package com.dicoding.kenalikan.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.kenalikan.ProfileActivity
import com.dicoding.kenalikan.R
import com.dicoding.kenalikan.databinding.FragmentHomeBinding
import com.dicoding.kenalikan.weatherclass.Clouds
import com.dicoding.kenalikan.weatherclass.WeatherTools
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {
    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding
    private lateinit var viewModel: HomeViewModel
    private var currentCityName: String = "Jakarta"
    private val CURRENT_CITY_KEY = "current_city_key"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        savedInstanceState?.let {
            currentCityName = it.getString(CURRENT_CITY_KEY, "Jakarta") ?: "Bandung"
        }

        val lastSearchedCityName = viewModel.savedStateHandle.get<String>(viewModel.lastSearchedCityNameKey)
        if (!lastSearchedCityName.isNullOrEmpty()) {
            fetchWeatherData(lastSearchedCityName)
        }

        binding.idProfile.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        fetchWeatherData(currentCityName)
        searchCity()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(CURRENT_CITY_KEY, currentCityName)
        super.onSaveInstanceState(outState)
    }

    private fun searchCity() {
        val searchView = binding.svSearch
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    fetchWeatherData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun fetchWeatherData(cityName: String) {
        viewModel.fetchWeatherData(cityName)
        viewModel.weatherData.observe(viewLifecycleOwner, Observer { weatherData ->
            weatherData?.let {
                updateUI(weatherData, cityName)
            }
        })
    }

    private fun updateUI(weatherData: WeatherTools, cityName: String) {
        currentCityName = cityName
        binding.apply {
            val temperature = weatherData.main.temp.toString()
            val windSpeed = weatherData.wind.speed
            val condition = weatherData.weather.firstOrNull()?.main ?: "unknown"
            val sunRise = weatherData.sys.sunrise.toLong()
            val sunSet = weatherData.sys.sunset.toLong()

            tvTemperature.text = "$temperature °C"
            tvWindspeed.text = "$windSpeed m/s"
            tvCondition.text = condition
            tvCity.text = cityName
            tvSunrise.text = "${time(sunRise)}"
            tvSunset.text = "${time(sunSet)}"
 

            changeImagesAccordingToWeatherCondition(condition)
        }
    }

    private fun time(timestamp: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format((Date(timestamp*1000)))
    }

    private fun changeImagesAccordingToWeatherCondition(conditions: String) {

        when (conditions){
            "Clear Sky", "Sunny", "Clear" ->{
                binding.ivWeatherIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.svg_sun))
            }
            "Haze", "Partly Clouds", "Clouds", "Overcast", "Mist", "Foggy" -> {
                binding.ivWeatherIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.svg_clouds))
            }
            "Thunderstorm" ,"Rain", "Light Rain", "Drizzle", "Moderate Rain", "Showers", "Heavy Rain" -> {
                binding.ivWeatherIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.svg_rain))
            }
            else ->{
                binding.ivWeatherIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.svg_sun))
            }

        }
        isRemoving
    }
}