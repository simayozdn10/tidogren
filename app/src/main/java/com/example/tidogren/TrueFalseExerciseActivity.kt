package com.example.tidogren

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kotlin.random.Random

class TrueFalseActivity : AppCompatActivity() {

    data class TFItem(val mediaRes: Int, val isVideo: Boolean, val correctWord: String)

    private val questions = listOf(
        TFItem(R.drawable.n_isaret2, false, "N"),
        TFItem(R.drawable.c_fingersnap_isaret2, false, "Ç"),
        TFItem(R.drawable.l_isaret2, false, "L"),
        TFItem(R.drawable.o_fingersnap_isaret2, false, "Ö"),
        TFItem(R.drawable.j_isaret2, false, "J"),
        TFItem(R.drawable.e_isaret2, false, "E"),
        TFItem(R.drawable.d_isaret2, false, "D"),
        TFItem(R.raw.belki, true, "Belki"),
        TFItem(R.raw.bugun, true, "Bugün"),
        TFItem(R.raw.tekrar, true, "Tekrar"),
        TFItem(R.raw.yarin, true, "Yarın"),
        TFItem(R.raw.kotu, true, "Kötü"),
        TFItem(R.raw.biz, true, "Biz"),
        TFItem(R.raw.sen1, true, "Sen")
    )

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var imageView: ImageView
    private lateinit var wordLabel: TextView
    private lateinit var scoreText: TextView

    private var currentIndex = 0
    private var score = 0
    private var correctCount = 0
    private var wrongCount = 0
    private var isCorrectChoice = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_true_false)

        playerView = findViewById(R.id.tfPlayerView)
        imageView = findViewById(R.id.tfImageView)
        wordLabel = findViewById(R.id.wordLabel)
        scoreText = findViewById(R.id.scoreText)

        player = ExoPlayer.Builder(this).build()
        playerView.player = player
        playerView.useController = false

        // 👇 videoya basınca tekrar oynat
        playerView.setOnClickListener {
            player.seekTo(0)
            player.play()
        }

        findViewById<Button>(R.id.btnTrue).setOnClickListener { checkAnswer(true) }
        findViewById<Button>(R.id.btnFalse).setOnClickListener { checkAnswer(false) }

        loadQuestion()
    }

    private fun loadQuestion() {

        if (currentIndex >= questions.size) {
            showResult()
            return
        }

        val currentItem = questions[currentIndex]
        isCorrectChoice = Random.nextBoolean()

        if (isCorrectChoice) {
            wordLabel.text = currentItem.correctWord
        } else {
            val wrongList = questions.filter { it.correctWord != currentItem.correctWord }
            wordLabel.text = wrongList.random().correctWord
        }

        if (currentItem.isVideo) {

            imageView.visibility = View.GONE
            playerView.visibility = View.VISIBLE

            val uri = Uri.parse("android.resource://$packageName/${currentItem.mediaRes}")
            player.setMediaItem(MediaItem.fromUri(uri))
            player.prepare()
            player.play()

        } else {

            player.stop()
            playerView.visibility = View.GONE
            imageView.visibility = View.VISIBLE

            imageView.setImageResource(currentItem.mediaRes)
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {

        if (userAnswer == isCorrectChoice) {
            score += 10
            correctCount++
            Toast.makeText(this, "Doğru! 🎉", Toast.LENGTH_SHORT).show()
        } else {
            wrongCount++
            Toast.makeText(this, "Yanlış! ❌", Toast.LENGTH_SHORT).show()
        }

        scoreText.text = "Puan: $score"

        currentIndex++
        loadQuestion()
    }

    private fun showResult() {

        // 📊 RAPOR KAYDET
        val sharedPref = getSharedPreferences("stats", MODE_PRIVATE)
        val editor = sharedPref.edit()

        val totalCorrect = sharedPref.getInt("correct", 0)
        val totalWrong = sharedPref.getInt("wrong", 0)
        val totalTest = sharedPref.getInt("test", 0)

        editor.putInt("correct", totalCorrect + correctCount)
        editor.putInt("wrong", totalWrong + wrongCount)
        editor.putInt("test", totalTest + 1)

        editor.apply()

        // 📊 SONUÇ GÖSTER
        AlertDialog.Builder(this)
            .setTitle("Test Bitti")
            .setMessage(
                """
Puan: $score

Doğru: $correctCount
Yanlış: $wrongCount
                """.trimIndent()
            )
            .setPositiveButton("Tamam") { _, _ ->
                finish()
            }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}