package com.example.horoscopo_android

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/v1/get-horoscope/daily")
    suspend fun getHoroscopo(
        @Query("sign") sign: String,
        @Query("day") day: String
    ): HoroscopoDetailResponse
}
