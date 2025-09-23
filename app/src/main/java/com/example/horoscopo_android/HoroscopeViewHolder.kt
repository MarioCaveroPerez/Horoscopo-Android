package com.example.horoscopo_android

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HoroscopeViewHolder(view: View): RecyclerView.ViewHolder(view)  {

    val nameTextView: TextView = view.findViewById(R.id.tvNameHoroscopo)
    val dateTextView: TextView = view.findViewById(R.id.tvDateHoroscopo)
    val iconImageView: ImageView = view.findViewById(R.id.imageHoroscopo)

    fun bind(horoscopo: Horoscopo){
        nameTextView.setText(horoscopo.name)
        dateTextView.setText(horoscopo.dates)
        iconImageView.setImageResource(horoscopo.icon)
    }
}