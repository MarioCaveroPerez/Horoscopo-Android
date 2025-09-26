package com.example.horoscopo_android.Activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.horoscopo_android.MainActivity
import com.example.horoscopo_android.R

class AmorActivity : AppCompatActivity() {

    private val mensajes = listOf(
        "Hoy tendrás un día romántico e inesperado.",
        "Tu amor propio se fortalece, aprovéchalo.",
        "Alguien especial pensará en ti hoy.",
        "Momentos de conexión profunda llegarán pronto.",
        "El amor está en el aire, abre tu corazón."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amor)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "AMOR"

        val layout = findViewById<ConstraintLayout>(R.id.loveLayout)
        val texto = findViewById<TextView>(R.id.tvLoveMessage)
        texto.visibility = View.INVISIBLE

        layout.setOnLongClickListener {
            val mensajeAleatorio = mensajes.random()
            texto.text = mensajeAleatorio
            texto.visibility = View.VISIBLE
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
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