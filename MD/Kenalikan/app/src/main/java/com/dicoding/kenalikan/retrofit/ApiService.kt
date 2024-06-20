package com.dicoding.kenalikan.retrofit

import com.dicoding.kenalikan.data.weatherclass.WeatherTools
import com.dicoding.kenalikan.response.PostResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    fun getWeatherData(
        @Query("q") city:String,
        @Query("appid") appid:String,
        @Query("units") units:String
    ) : Call<WeatherTools>

    @Multipart
    @POST("predict")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): PostResponse
}
