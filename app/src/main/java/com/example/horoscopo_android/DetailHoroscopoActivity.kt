package com.example.horoscopo_android

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DetailHoroscopoActivity : AppCompatActivity() {

    private lateinit var btnFavorite: ImageButton
    private var isFavorite = false
    private lateinit var signName: String
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: DescriptionPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_horoscopo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnFavorite = findViewById(R.id.btnFavorite)
        signName = intent.getStringExtra("id") ?: "aries"

        val prefs = getSharedPreferences("favorites", Context.MODE_PRIVATE)
        isFavorite = prefs.getBoolean(signName, false)
        updateFavoriteIcon()

        btnFavorite.setOnClickListener {
            isFavorite = !isFavorite
            updateFavoriteIcon()
            prefs.edit().putBoolean(signName, isFavorite).apply()

            val resultIntent = Intent().apply {
                putExtra("id", signName)
                putExtra("isFavorite", isFavorite)
            }
            setResult(Activity.RESULT_OK, resultIntent)
        }

        val imageView = findViewById<ImageView>(R.id.imageHoroscopoDetail)
        val nameTextView = findViewById<TextView>(R.id.tvHoroscopoNameDetail)
        val dateTextView = findViewById<TextView>(R.id.tvDateHoroscopoDetail)
        viewPager = findViewById<ViewPager2>(R.id.tvDescriptionHoroscopoDetail)

        val name = intent.getStringExtra("name")
        val dates = intent.getStringExtra("dates")
        val iconResId = intent.getIntExtra("icon", -1)

        if (iconResId != -1) imageView.setImageResource(iconResId)

        nameTextView.text = name
        dateTextView.text = dates
        adapter = DescriptionPagerAdapter(
            listOf(
                "Cargando predicción diaria...",
                "Cargando predicción semanal...",
                "Cargando predicción mensual..."
            )
        )
        viewPager.adapter = adapter

        val sign = when (name?.lowercase()) {
            "aries" -> "aries"
            "tauro" -> "taurus"
            "geminis" -> "gemini"
            "cancer" -> "cancer"
            "leo" -> "leo"
            "virgo" -> "virgo"
            "libra" -> "libra"
            "escorpio" -> "scorpio"
            "sagitario" -> "sagittarius"
            "capricornio" -> "capricorn"
            "acuario" -> "aquarius"
            "piscis" -> "pisces"
            else -> "aries"
        }


        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = getRetrofit().create(ApiService::class.java)

                val dailyResponse = apiService.getHoroscopodaily(sign, "today")
                val weeklyResponse = apiService.getHoroscopoweekly(sign)
                val monthlyResponse = apiService.getHoroscopomonthly(sign)

                withContext(Dispatchers.Main) {
                    adapter = DescriptionPagerAdapter(
                        listOf(
                            dailyResponse.data.descriptionHoroscopo,
                            weeklyResponse.data.descriptionHoroscopo,
                            monthlyResponse.data.descriptionHoroscopo
                        )
                    )
                    viewPager.adapter = adapter
                }
            } catch (e: Exception) {
                e.printStackTrace() // Para ver el error real
                withContext(Dispatchers.Main) {
                    adapter = DescriptionPagerAdapter(
                        listOf(
                            "Error al cargar predicción diaria.",
                            "Error al cargar predicción semanal.",
                            "Error al cargar predicción mensual."
                        )
                    )
                    viewPager.adapter = adapter
                }
            }
        }
    }


    private fun updateFavoriteIcon() {
        val drawable = if (isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
        btnFavorite.setImageResource(drawable)
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://horoscope-app-api.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}