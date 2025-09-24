package com.example.horoscopo_android

import com.google.gson.annotations.SerializedName

data class HoroscopoDeatilResponse(
    @SerializedName("date") val date: String,
    @SerializedName("horoscope_data") val descriptionHoroscopo: String
)