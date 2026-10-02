package com.example.tidogren

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlphabetAdapter(
    private val alphabetList: List<AlphabetItem>
) : RecyclerView.Adapter<AlphabetAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgLetter: ImageView = itemView.findViewById(R.id.imgLetter)
        val txtLetter: TextView = itemView.findViewById(R.id.txtLetter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alphabet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = alphabetList[position]
        holder.txtLetter.text = item.letter
        holder.imgLetter.setImageResource(item.imageResId) // 🔥 FOTOĞRAFI BAĞLAYAN SATIR
    }

    override fun getItemCount() = alphabetList.size
}
