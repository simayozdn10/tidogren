package com.example.tidogren

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class MatchingActivity : AppCompatActivity() {

    data class MatchItem(val id: Int, val mediaRes: Int, val isVideo: Boolean, val word: String)

    private val allItems = listOf(
        MatchItem(1, R.drawable.u_fingersnap_isaret2, false, "Ü"),
        MatchItem(2, R.drawable.g_fingersnap_isaret2, false, "Ğ"),
        MatchItem(3, R.drawable.p_isaret2, false, "P"),
        MatchItem(4, R.drawable.s_fingersnap_isaret2, false, "Ş"),
        MatchItem(5, R.drawable.c_isaret2, false, "C"),
        MatchItem(6, R.drawable.d_isaret2, false, "D"),
        MatchItem(7, R.drawable.f_isaret2, false, "F"),
        MatchItem(8, R.raw.aksam, true, "Akşam"),
        MatchItem(9, R.raw.ogretmen1, true, "Öğretmen"),
        MatchItem(10, R.raw.baba, true, "Baba"),
        MatchItem(11, R.raw.susamak, true, "Susamak"),
        MatchItem(12, R.raw.yorgun, true, "Yorgun")
    )

    private var selectedPlayerId: Int? = null
    private var selectedWordId: Int? = null

    private val playerContainers = mutableMapOf<Int, FrameLayout>()
    private val wordButtons = mutableMapOf<Int, Button>()
    private val activePlayers = mutableListOf<ExoPlayer>()

    private var correctCount = 0
    private var wrongCount = 0

    private val activityName = "MatchingActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching)

        setupGame()
    }

    private fun setupGame() {

        val mainContainer = findViewById<LinearLayout>(R.id.mainContainer)

        val shuffledMedia = allItems.shuffled()
        val shuffledWords = allItems.shuffled()

        for (i in allItems.indices) {

            val rowLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(-1, -2)
                weightSum = 2f
                gravity = Gravity.CENTER_VERTICAL
                setPadding(0, 10, 0, 10)
            }

            // SOL TARAF
            val mediaItem = shuffledMedia[i]

            val container = FrameLayout(this).apply {
                layoutParams = LinearLayout.LayoutParams(0, 300, 1f)
                setBackgroundResource(android.R.drawable.dialog_holo_light_frame)
            }

            if (mediaItem.isVideo) {

                val pv = PlayerView(this).apply {
                    layoutParams = FrameLayout.LayoutParams(-1, -1)
                    useController = false
                }

                val player = ExoPlayer.Builder(this).build().apply {
                    setMediaItem(
                        MediaItem.fromUri(
                            Uri.parse("android.resource://$packageName/${mediaItem.mediaRes}")
                        )
                    )
                    repeatMode = ExoPlayer.REPEAT_MODE_ALL
                    prepare()
                    play()
                    volume = 0f
                }

                pv.setOnClickListener {
                    player.seekTo(0)
                    player.play()
                }

                pv.player = player
                activePlayers.add(player)
                container.addView(pv)

            } else {
                val iv = ImageView(this).apply {
                    layoutParams = FrameLayout.LayoutParams(-1, -1)
                    setImageResource(mediaItem.mediaRes)
                }
                container.addView(iv)
            }

            container.setOnClickListener {
                resetLeftSelection()
                selectedPlayerId = mediaItem.id
                container.setBackgroundColor(Color.parseColor("#3498db"))
                checkMatch()
            }

            playerContainers[mediaItem.id] = container

            // SAĞ TARAF
            val wordItem = shuffledWords[i]

            val btn = Button(this).apply {
                text = wordItem.word
                layoutParams = LinearLayout.LayoutParams(0, 300, 1f)
                setBackgroundColor(Color.WHITE)
                setTextColor(Color.BLACK)
                setAllCaps(false)
                textSize = 18f
            }

            btn.setOnClickListener {
                resetRightSelection()
                selectedWordId = wordItem.id
                btn.setBackgroundColor(Color.parseColor("#3498db"))
                checkMatch()
            }

            wordButtons[wordItem.id] = btn

            rowLayout.addView(container)
            rowLayout.addView(btn)
            mainContainer.addView(rowLayout)
        }
    }

    private fun checkMatch() {

        if (selectedPlayerId != null && selectedWordId != null) {

            if (selectedPlayerId == selectedWordId) {

                correctCount++

                playerContainers[selectedPlayerId!!]?.apply {
                    isEnabled = false
                    alpha = 0.3f
                    setBackgroundColor(Color.GREEN)
                }

                wordButtons[selectedWordId!!]?.apply {
                    isEnabled = false
                    setBackgroundColor(Color.GREEN)
                    setTextColor(Color.WHITE)
                }

                Toast.makeText(this, "Doğru!", Toast.LENGTH_SHORT).show()

                checkGameFinished()

            } else {

                wrongCount++

                Toast.makeText(this, "Yanlış!", Toast.LENGTH_SHORT).show()

                resetLeftSelection()
                resetRightSelection()
            }

            selectedPlayerId = null
            selectedWordId = null
        }
    }

    private fun checkGameFinished() {

        if (wordButtons.values.all { !it.isEnabled }) {

            val sharedPref = getSharedPreferences("stats", MODE_PRIVATE)
            val editor = sharedPref.edit()

            val prevCorrect = sharedPref.getInt("${activityName}_correct", 0)
            val prevWrong = sharedPref.getInt("${activityName}_wrong", 0)
            val prevPlay = sharedPref.getInt("${activityName}_play", 0)

            editor.putInt("${activityName}_correct", prevCorrect + correctCount)
            editor.putInt("${activityName}_wrong", prevWrong + wrongCount)
            editor.putInt("${activityName}_play", prevPlay + 1)

            editor.apply()

            AlertDialog.Builder(this)
                .setTitle("Oyun Bitti")
                .setMessage("Doğru: $correctCount\nYanlış: $wrongCount")
                .setPositiveButton("Tamam") { _, _ -> finish() }
                .show()
        }
    }

    private fun resetLeftSelection() {
        playerContainers.values.filter { it.isEnabled }.forEach {
            it.setBackgroundResource(android.R.drawable.dialog_holo_light_frame)
        }
    }

    private fun resetRightSelection() {
        wordButtons.values.filter { it.isEnabled }.forEach {
            it.setBackgroundColor(Color.WHITE)
            it.setTextColor(Color.BLACK)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activePlayers.forEach { it.release() }
    }
}