package com.example.horoscopo_android.Activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.horoscopo_android.MainActivity
import com.example.horoscopo_android.R
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat

class AmorActivity : AppCompatActivity() {

    private lateinit var mensajes: List<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amor)

        mensajes = listOf(
            getString(R.string.mensaje_muy_bueno),
            getString(R.string.mensaje_bueno),
            getString(R.string.mensaje_normal),
            getString(R.string.mensaje_malo),
            getString(R.string.mensaje_muy_malo)
        )

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Saber tu Fortuna"

        val layout = findViewById<ConstraintLayout>(R.id.loveLayout)
        val texto = findViewById<TextView>(R.id.tvLoveMessage)
        val titulo = findViewById<TextView>(R.id.tvTittleAmor)
        val circulo = findViewById<ImageView>(R.id.image_circle_amor)
        val button = findViewById<Button>(R.id.btn_probar_otra_vez)
        val text = findViewById<TextView>(R.id.tv_amor)
        val tittle_amor = findViewById<TextView>(R.id.tv_tittle_amor)
        texto.visibility = View.INVISIBLE

        val holdTime = 2500L
        val handler = Handler(Looper.getMainLooper())

        layout.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    circulo.setImageResource(R.drawable.ic_circle_filled)

                    handler.postDelayed({
                        val mensajeAleatorio = mensajes.random()
                        texto.text = mensajeAleatorio
                        texto.visibility = View.VISIBLE
                        button.visibility = View.VISIBLE
                        text.visibility = View.VISIBLE
                        tittle_amor.visibility = View.VISIBLE

                        titulo.visibility = View.GONE
                        circulo.visibility = View.GONE

                        when (mensajeAleatorio) {
                            getString(R.string.mensaje_muy_bueno) -> {
                                text.text = "MUY BUENA"
                                text.setTextColor(ContextCompat.getColor(this, R.color.green))
                            }

                            getString(R.string.mensaje_bueno) -> {
                                text.text = "BUENA"
                                text.setTextColor(ContextCompat.getColor(this, R.color.blue))
                            }

                            getString(R.string.mensaje_normal) -> {
                                text.text = "NORMAL"
                                text.setTextColor(ContextCompat.getColor(this, R.color.brown))
                            }

                            getString(R.string.mensaje_malo) -> {
                                text.text = "MALA"
                                text.setTextColor(ContextCompat.getColor(this, R.color.orange))
                            }

                            getString(R.string.mensaje_muy_malo) -> {
                                text.text = "MUY MALA"
                                text.setTextColor(ContextCompat.getColor(this, R.color.red))
                            }

                            else -> text.text = ""
                        }
                    }, holdTime)
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    handler.removeCallbacksAndMessages(null)
                    // Restaurar circulo a su estado inicial si no se completó el hold
                    circulo.setImageResource(R.drawable.ic_circle)
                }
            }
            true
        }

        button.setOnClickListener {
            val intent = intent
            finish()
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_signos -> {
                startActivity(Intent(this, MainActivity::class.java))
            }

            R.id.menu_lazos -> {
                startActivity(Intent(this, ZodiacCompatibilityActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}