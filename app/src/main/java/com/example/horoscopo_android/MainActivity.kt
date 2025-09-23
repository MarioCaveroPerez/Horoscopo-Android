package com.example.horoscopo_android

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    val horoscopoList: List<Horoscopo> = listOf(
        Horoscopo("Aries", 0, 0, 0),
        Horoscopo("Tauro", 0, 0, 0),
        Horoscopo("Geminis", 0, 0, 0),
        Horoscopo("Cancer", 0, 0, 0),
        Horoscopo("Leo", 0, 0, 0),
        Horoscopo("Virgo", 0, 0, 0),
        Horoscopo("Libra", 0, 0, 0),
        Horoscopo("Escorpio", 0, 0, 0),
        Horoscopo("Sagitario", 0, 0, 0),
        Horoscopo("Capricornio", 0, 0, 0),
        Horoscopo("Acuario", 0, 0, 0),
        Horoscopo("Piscis", 0, 0, 0),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}