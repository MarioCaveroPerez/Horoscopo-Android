package com.example.horoscopo_android

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        val dateTextView = findViewById<TextView>(R.id.tvDateHoroscopoDetail)
        val dailyDescriptionTextView = findViewById<TextView>(R.id.tvDescriptionHoroscopoDetail)

        val name = intent.getStringExtra("name")
        val dates = intent.getStringExtra("dates")
        val iconResId = intent.getIntExtra("icon", -1)

        if (iconResId != -1) imageView.setImageResource(iconResId)

        nameTextView.text = name
        dateTextView.text = dates
        dailyDescriptionTextView.text = "Cargando predicción..."


        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val sign = name?.replaceFirstChar { it.uppercase() } ?: return


        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = getRetrofit().create(ApiService::class.java)
                val response = apiService.getHoroscopo(sign, today)

                withContext(Dispatchers.Main) {
                    dailyDescriptionTextView.text = response.descriptionHoroscopo
                }
            } catch (e: Exception) {
                e.printStackTrace() // Para ver el error real
                withContext(Dispatchers.Main) {
                    dailyDescriptionTextView.text = "Error al cargar la predicción."
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://horoscope-app-api.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}