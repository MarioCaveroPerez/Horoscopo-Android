package com.example.horoscopo_android.Activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.horoscopo_android.Data.Horoscopo
import com.example.horoscopo_android.MainActivity
import com.example.horoscopo_android.R

class ZodiacCompatibilityActivity : AppCompatActivity() {

    private lateinit var spZodiac1: Spinner
    private lateinit var spZodiac2: Spinner
    private lateinit var btnNext: Button

    private lateinit var horoscopo: List<Horoscopo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zodiac_compatibility)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Lazos Zodiacales"

        spZodiac1 = findViewById<Spinner>(R.id.spZodiac1)
        spZodiac2 = findViewById<Spinner>(R.id.spZodiac2)
        btnNext = findViewById(R.id.btnNext)

        horoscopo = Horoscopo.getAll()

        val nombreSigno = horoscopo.map { getString(it.name) }

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            nombreSigno
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spZodiac1.adapter = adapter
        spZodiac2.adapter = adapter

        btnNext.setOnClickListener {
            val signo1 = horoscopo[spZodiac1.selectedItemPosition]
            val signo2 = horoscopo[spZodiac2.selectedItemPosition]

            val intent = Intent(this, ResultZodiacCompatibilityActivity::class.java)
            intent.putExtra("SIGNO_1", signo1.id)
            intent.putExtra("SIGNO_2", signo2.id)
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

            R.id.menu_amor -> {
                startActivity(Intent(this, AmorActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}