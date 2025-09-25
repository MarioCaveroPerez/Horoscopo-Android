package com.example.horoscopo_android.Adapters

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.horoscopo_android.Data.Horoscopo
import com.example.horoscopo_android.R

class HoroscopeViewHolder(view: View): RecyclerView.ViewHolder(view)  {

        val nameTextView: TextView = view.findViewById(R.id.tvNameHoroscopo)
        val dateTextView: TextView = view.findViewById(R.id.tvDateHoroscopo)
        val iconImageView: ImageView = view.findViewById(R.id.imageHoroscopo)
        val favoriteImageView: ImageView = view.findViewById(R.id.ivFavorite)

        fun bind(horoscopo: Horoscopo){
            val context = itemView.context
            nameTextView.setText(horoscopo.name)
            dateTextView.setText(horoscopo.dates)
            iconImageView.setImageResource(horoscopo.icon)

            val prefs = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
            val isFavorite = prefs.getBoolean(horoscopo.id, false)
            favoriteImageView.visibility = if (isFavorite) View.VISIBLE else View.GONE
        }
    }