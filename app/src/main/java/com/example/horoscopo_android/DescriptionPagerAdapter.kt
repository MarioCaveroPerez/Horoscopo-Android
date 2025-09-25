package com.example.horoscopo_android

import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DescriptionPagerAdapter(
    private val descriptions: List<String>
) : RecyclerView.Adapter<DescriptionPagerAdapter.DescriptionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescriptionViewHolder {
        val tv = TextView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            textSize = 18f
            gravity = Gravity.CENTER
        }
        return DescriptionViewHolder(tv)
    }

    override fun onBindViewHolder(holder: DescriptionViewHolder, position: Int) {
        holder.textView.text = descriptions[position]
    }

    override fun getItemCount(): Int = descriptions.size

    class DescriptionViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

}


