package com.example.tidogren

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView

class WordsAdapter(
    private val wordList: List<WordItem>
) : RecyclerView.Adapter<WordsAdapter.WordViewHolder>() {

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtWord: TextView = itemView.findViewById(R.id.txtWord)
        val videoWord: VideoView = itemView.findViewById(R.id.videoWord)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_word_card, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {

        val item = wordList[position]
        holder.txtWord.text = item.word

        val context = holder.itemView.context
        val uri = Uri.parse(
            "android.resource://${context.packageName}/${item.videoList[0]}"
        )

        holder.videoWord.setVideoURI(uri)

        holder.videoWord.setOnPreparedListener {
            holder.videoWord.start()
        }
    }

    override fun getItemCount() = wordList.size
}