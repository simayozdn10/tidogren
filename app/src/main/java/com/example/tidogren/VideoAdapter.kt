package com.example.tidogren

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView

class VideoAdapter(private val videoList: List<Pair<Int, String>>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerView: PlayerView = itemView.findViewById(R.id.playerView)
        val titleText: TextView = itemView.findViewById(R.id.videoTitle)
        var player: ExoPlayer? = null

        fun bind(videoResId: Int, title: String) {
            titleText.text = title

            // Önceki player'ı tamamen temizle
            releasePlayer()

            // Yeni Player kurulumu
            player = ExoPlayer.Builder(itemView.context).build().apply {
                playerView.player = this

                val videoUri = Uri.parse("android.resource://${itemView.context.packageName}/$videoResId")
                val mediaItem = MediaItem.fromUri(videoUri)

                setMediaItem(mediaItem)
                repeatMode = Player.REPEAT_MODE_ONE

                // HATA TAKİBİ: Siyah ekranın nedenini Logcat'ten görebiliriz
                addListener(object : Player.Listener {
                    override fun onPlayerError(error: PlaybackException) {
                        Log.e("ExoPlayerError", "$title videosu yüklenemedi: ${error.message}")
                    }
                })

                prepare()
                playWhenReady = true
            }
        }

        fun releasePlayer() {
            // Player'ı serbest bırakırken sırayla gitmek donmaları engeller
            player?.let {
                it.stop()
                it.release()
            }
            player = null
            playerView.player = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val (resId, title) = videoList[position]
        holder.bind(resId, title)
    }

    // Video ekrandan çıktığında (kaydırıldığında) kaynakları öldür
    override fun onViewDetachedFromWindow(holder: VideoViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.releasePlayer()
    }

    // Video tekrar ekrana geldiğinde (geri kaydırıldığında) tekrar başlat
    override fun onViewAttachedToWindow(holder: VideoViewHolder) {
        super.onViewAttachedToWindow(holder)
        val position = holder.bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION) {
            val (resId, title) = videoList[position]
            holder.bind(resId, title)
        }
    }

    override fun getItemCount(): Int = videoList.size
}