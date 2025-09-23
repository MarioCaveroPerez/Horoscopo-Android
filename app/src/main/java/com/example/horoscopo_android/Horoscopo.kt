package com.example.horoscopo_android

data class Horoscopo(
    val id: String,
    val name: Int,
    val dates: Int,
    val icon: Int
) {

    companion object{
        fun getAll(): List<Horoscopo>{
            return listOf(
                Horoscopo("aries", R.string.horoscope_name_aries, R.string.horoscope_date_aries, R.drawable.ic_aries),
                Horoscopo("tauro", R.string.horoscope_name_taurus, R.string.horoscope_date_taurus, R.drawable.ic_tauro),
                Horoscopo("geminis",R.string.horoscope_name_gemini , R.string.horoscope_date_gemini, R.drawable.ic_gemini),
                Horoscopo("cancer", R.string.horoscope_name_cancer, R.string.horoscope_date_cancer, R.drawable.ic_cancer),
                Horoscopo("leo", R.string.horoscope_name_leo, R.string.horoscope_date_leo, R.drawable.ic_leo),
                Horoscopo("virgo", R.string.horoscope_name_virgo, R.string.horoscope_date_virgo, R.drawable.ic_virgo),
                Horoscopo("libra", R.string.horoscope_name_libra, R.string.horoscope_date_libra, R.drawable.ic_libra),
                Horoscopo("escorpio", R.string.horoscope_name_scorpio, R.string.horoscope_date_scorpio, R.drawable.ic_escorpio),
                Horoscopo("sagitario", R.string.horoscope_name_sagittarius, R.string.horoscope_date_sagittarius, R.drawable.ic_sagitario),
                Horoscopo("capricornio", R.string.horoscope_name_capricorn, R.string.horoscope_date_capricorn, R.drawable.ic_capricornio),
                Horoscopo("acuario", R.string.horoscope_name_aquarius, R.string.horoscope_date_aquarius, R.drawable.ic_acuario),
                Horoscopo("piscis", R.string.horoscope_name_pisces, R.string.horoscope_date_pisces, R.drawable.ic_piscis),
            )
        }
    }
}