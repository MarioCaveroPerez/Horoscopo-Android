package com.example.horoscopo_android.Utils

import com.example.horoscopo_android.Data.HoroscopoDetailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/v1/get-horoscope/daily")
    suspend fun getHoroscopodaily(
        @Query("sign") sign: String,
        @Query("day") day: String
    ): HoroscopoDetailResponse

    @GET("/api/v1/get-horoscope/weekly")
    suspend fun getHoroscopoweekly(
        @Query("sign") sign: String
    ): HoroscopoDetailResponse

    @GET("/api/v1/get-horoscope/monthly")
    suspend fun getHoroscopomonthly(
        @Query("sign") sign: String
    ): HoroscopoDetailResponse
}