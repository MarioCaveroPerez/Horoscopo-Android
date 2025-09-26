package com.example.horoscopo_android

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.horoscopo_android.Activities.AmorActivity
import com.example.horoscopo_android.Activities.ZodiacCompatibilityActivity

class Activity_menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Menú"
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
            R.id.menu_amor -> {
                startActivity(Intent(this, AmorActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}