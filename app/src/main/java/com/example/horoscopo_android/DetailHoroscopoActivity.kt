package com.example.horoscopo_android

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailHoroscopoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_horoscopo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imageView = findViewById<ImageView>(R.id.imageHoroscopoDetail)
        val nameTextView = findViewById<TextView>(R.id.tvHoroscopoNameDetail)
        val descriptionTextView = findViewById<TextView>(R.id.tvDateHoroscopoDetail)

        // Recuperar datos del Intent
        val name = intent.getStringExtra("name")
        val dates = intent.getStringExtra("dates")
        val iconResId = intent.getIntExtra("icon", -1)

        // ✅ Mostramos la información
        if (iconResId != -1) {
            imageView.setImageResource(iconResId)
        }

        nameTextView.text = name
        descriptionTextView.text = dates
    }

    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://horoscope-app-api.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}