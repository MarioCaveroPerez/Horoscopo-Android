package com.example.horoscopo_android

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputBinding
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.search.SearchBar
import com.ignite.material.searchbarview.SearchBarView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.Normalizer

class MainActivity : AppCompatActivity() {

    lateinit var sbvHoroscopo: SearchBarView
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: HoroscopeAdapter

    val horoscopoList: List<Horoscopo> = Horoscopo.getAll()

    private val detailLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val id = result.data?.getStringExtra("id")
            val isFavorite = result.data?.getBooleanExtra("isFavorite", false) ?: false

            // Actualizamos la lista y refrescamos adapter
            id?.let {
                val prefs = getSharedPreferences("favorites", Context.MODE_PRIVATE)
                prefs.edit().putBoolean(it, isFavorite).apply()
                adapter.notifyDataSetChanged() // Se recargará el icono de favoritos
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sbvHoroscopo = findViewById(R.id.sbvHoroscope)
        recyclerView = findViewById(R.id.rvHoroscope)

        adapter = HoroscopeAdapter(horoscopoList) { selectedHoroscopo ->
            val intent = Intent(this, DetailHoroscopoActivity::class.java).apply {
                putExtra("id", selectedHoroscopo.id)
                putExtra("name", getString(selectedHoroscopo.name))
                putExtra("dates", getString(selectedHoroscopo.dates))
                putExtra("icon", selectedHoroscopo.icon)
            }
            detailLauncher.launch(intent)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        initUI()
    }

    private fun initUI() {
        sbvHoroscopo.setOnQueryTextChangeListener { query ->
            searByName(query)
        }
    }

    private fun searByName(query: String) {
        val normalizedQuery = normalize(query)

        val filteredList = horoscopoList.filter { horoscopo ->
            val horoscopoName = getString(horoscopo.name)
            normalize(horoscopoName).contains(normalizedQuery)
        }
        adapter.updateList(filteredList)
    }

    private fun normalize(text: String): String {
        return Normalizer.normalize(text, Normalizer.Form.NFD)
            .replace("\\p{Mn}+".toRegex(), "") // Elimina marcas diacríticas (acentos)
            .lowercase()
    }

}