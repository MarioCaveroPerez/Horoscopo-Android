package com.example.horoscopo_android.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.horoscopo_android.Data.Horoscopo
import com.example.horoscopo_android.R

class HoroscopeAdapter(
    var horoscopeList: List<Horoscopo>, val onItemClick: (Horoscopo) -> Unit
) : RecyclerView.Adapter<HoroscopeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoroscopeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_horoscopo, parent, false)
        return HoroscopeViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: HoroscopeViewHolder, position: Int
    ) {
        val item = horoscopeList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount() = horoscopeList.size

    fun updateList(newList: List<Horoscopo>) {
        horoscopeList = newList
        notifyDataSetChanged()
    }
}
