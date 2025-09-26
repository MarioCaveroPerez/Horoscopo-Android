package com.example.horoscopo_android.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.horoscopo_android.Adapters.DescriptionPagerAdapter
import com.example.horoscopo_android.Data.Horoscopo
import com.example.horoscopo_android.Utils.ApiService
import com.example.horoscopo_android.R
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

class DetailHoroscopoActivity : AppCompatActivity() {

    val currentLocale = Locale.getDefault()
    val languageCode = currentLocale.language
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


        val tvTipoSigno = findViewById<TextView>(R.id.tvTipoSigno)
        val idRecibido = intent.getStringExtra("id") ?: ""
        val horoscopoSeleccionado = Horoscopo.getAll().firstOrNull{it.id == idRecibido}

        if(horoscopoSeleccionado != null) {
            when (horoscopoSeleccionado.id.lowercase()) {
                "cancer", "escorpio", "piscis" -> {
                    tvTipoSigno.text = "AGUA"
                    tvTipoSigno.setTextColor(ContextCompat.getColor(this, R.color.blue))
                }

                "aries", "leo", "sagitario" -> {
                    tvTipoSigno.text = "FUEGO"
                    tvTipoSigno.setTextColor(ContextCompat.getColor(this, R.color.red))
                }

                "geminis", "libra", "acuario" -> {
                    tvTipoSigno.text = "AIRE"
                    tvTipoSigno.setTextColor(ContextCompat.getColor(this, R.color.green))
                }

                "tauro", "virgo", "capricornio" -> {
                    tvTipoSigno.text = "TIERRA"
                    tvTipoSigno.setTextColor(ContextCompat.getColor(this, R.color.brown))
                }
            }
        }
        val btnShare = findViewById<ImageButton>(R.id.btnShare)
        btnShare.setOnClickListener {
            // Obtener el texto de la predicción diaria (posición 0)
            val currentPosition = viewPager.currentItem

            // Obtener el texto correspondiente de esa posición
            val textToShare = adapter.descriptions.getOrNull(currentPosition) ?: "No hay predicción disponible"

            // Crear el Intent para compartir
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textToShare)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Compartir predicción"))
        }

        btnFavorite = findViewById(R.id.btnFavorite)
        signName = intent.getStringExtra("id") ?: "aries"

        val prefs = getSharedPreferences("favorites", MODE_PRIVATE)
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
            setResult(RESULT_OK, resultIntent)
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

                val dailyText = dailyResponse.data.descriptionHoroscopo
                val weeklyText = weeklyResponse.data.descriptionHoroscopo
                val monthlyText = monthlyResponse.data.descriptionHoroscopo

                val deviceLanguage = Locale.getDefault().language
                val targetLangCode = when (deviceLanguage) {
                    "es" -> TranslateLanguage.SPANISH
                    "fr" -> TranslateLanguage.FRENCH
                    "de" -> TranslateLanguage.GERMAN
                    else -> TranslateLanguage.ENGLISH
                }

                suspend fun translateIfNeeded(text: String): String {
                    return if (targetLangCode != TranslateLanguage.ENGLISH) {
                        suspendCancellableCoroutine<String> { cont ->
                            translateText(text, targetLangCode) { translated ->
                                cont.resume(translated) {}
                            }
                        }
                    } else {
                        text
                    }
                }
                val translatedDaily = translateIfNeeded("DAILY PREDICTION:" + dailyText)
                val translatedWeekly = translateIfNeeded("WEEKLY PREDICTION:" + weeklyText)
                val translatedMonthly = translateIfNeeded("MONTHLY PREDICTION:" + monthlyText)

                withContext(Dispatchers.Main) {
                    adapter = DescriptionPagerAdapter(
                        listOf(translatedDaily, translatedWeekly, translatedMonthly)
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
    fun translateText(inputText: String, targetLanguage: String, onResult: (String) -> Unit) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(targetLanguage)
            .build()

        val translator: Translator = Translation.getClient(options)

        translator.downloadModelIfNeeded()
            .addOnSuccessListener {
                translator.translate(inputText)
                    .addOnSuccessListener { translatedText ->
                        onResult(translatedText)
                    }
                    .addOnFailureListener { e ->
                        e.printStackTrace()
                        onResult(inputText)
                    }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                onResult(inputText)
            }
    }
}