package com.example.horoscopo_android.Adapters

import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView

class DescriptionPagerAdapter(
    val descriptions: List<String>
) : RecyclerView.Adapter<DescriptionPagerAdapter.DescriptionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescriptionViewHolder {
        val tv = TextView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            textSize = 18f
            gravity = Gravity.CENTER
        }
        return DescriptionViewHolder(tv)
    }

    override fun onBindViewHolder(holder: DescriptionViewHolder, position: Int) {
        val rawText = descriptions[position]
        holder.textView.text = HtmlCompat.fromHtml(rawText, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    override fun getItemCount(): Int = descriptions.size

    class DescriptionViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

}