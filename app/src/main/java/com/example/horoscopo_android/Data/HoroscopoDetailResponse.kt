package com.example.horoscopo_android.Data

import com.google.gson.annotations.SerializedName


data class HoroscopoDetailResponse(
    @SerializedName("data") val data: HoroscopoData
)

data class HoroscopoData(
    @SerializedName("date") val date: String,
    @SerializedName("horoscope_data") val descriptionHoroscopo: String
)