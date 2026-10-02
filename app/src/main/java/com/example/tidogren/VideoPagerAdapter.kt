package com.example.tidogren

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView

class VideoPagerAdapter(
    private val wordList: List<WordItem>
) : RecyclerView.Adapter<VideoPagerAdapter.VideoViewHolder>() {

    private var currentPlayer: ExoPlayer? = null

    inner class VideoViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_video_pager, parent, false)
        ) {
        val playerView: PlayerView = itemView.findViewById(R.id.playerView)
        val text: TextView = itemView.findViewById(R.id.textWord)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(parent)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.text.text = wordList[position].word
    }

    fun playVideoAt(position: Int, holder: VideoViewHolder) {

        val context = holder.itemView.context
        val item = wordList[position]

        currentPlayer?.release()

        val player = ExoPlayer.Builder(context).build()
        holder.playerView.player = player

        val mediaItem = MediaItem.fromUri(
            "android.resource://${context.packageName}/${item.videoList[0]}"
        )

        player.setMediaItem(mediaItem)
        player.repeatMode = ExoPlayer.REPEAT_MODE_ONE
        player.prepare()
        player.play()

        currentPlayer = player
    }

    override fun getItemCount() = wordList.size
}