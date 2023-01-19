package com.hindgyan.hanumanchalisa.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hindgyan.hanumanchalisa.R

class HomeAdapter(
    private val hindiArray: ArrayList<String>,
    private val hindiMeaningArray: ArrayList<String>,
    private val meaning: Boolean
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvTittle: TextView = itemView.findViewById(R.id.tvTittle)
        val tvMeaning: TextView = itemView.findViewById(R.id.tvMeaning)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTittle.text = hindiArray[position]
        if (meaning) {
            holder.tvMeaning.visibility = View.VISIBLE

            holder.tvMeaning.text = hindiMeaningArray[position]
        } else
            holder.tvMeaning.visibility = View.GONE

    }

    override fun getItemCount(): Int {
        return hindiArray.size
    }
}