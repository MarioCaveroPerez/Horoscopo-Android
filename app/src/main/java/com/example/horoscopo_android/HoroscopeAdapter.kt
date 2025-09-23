package com.example.horoscopo_android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HoroscopeAdapter(
    val horoscopeList: List<Horoscopo>
) : RecyclerView.Adapter<HoroscopeViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HoroscopeViewHolder {
        return HoroscopeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_horoscopo, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: HoroscopeViewHolder,
        position: Int
    ) {
        val item = horoscopeList[position]
        holder.bind(item)
    }

    override fun getItemCount() = horoscopeList.size
}