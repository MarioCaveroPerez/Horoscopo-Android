package com.example.horoscopo_android

import com.google.gson.annotations.SerializedName

data class HoroscopoDetailResponse(
    @SerializedName("horoscope_data") val descriptionHoroscopo: String
)