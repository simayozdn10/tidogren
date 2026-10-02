package com.example.tidogren

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.appcompat.app.AlertDialog

class MultipleChoiceActivity : AppCompatActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var imageView: ImageView
    private lateinit var questionText: TextView
    private lateinit var buttons: List<Button>

    private var currentIndex = 0
    private var correctCount = 0
    private var wrongCount = 0

    data class Question(
        val mediaResId: Int,
        val isVideo: Boolean,
        val question: String,
        val options: List<String>,
        val correctIndex: Int
    )

    private val questions = listOf(
        Question(
            R.raw.aile,
            true,
            "Bu hangi kelimedir?",
            listOf("Anne", "Aile", "Baba", "Kardeş"),
            1
        ),
        Question(
            R.drawable.a_isaret2,
            false,
            "Bu hangi harftir?",
            listOf("A", "B", "C", "D"),
            0
        ),
        Question(
            R.raw.gunaydin,
            true,
            "Bu hangi kelimedir?",
            listOf("Merhaba", "Günaydın", "Lütfen", "Dün"),
            1
        ),
        Question(
            R.drawable.m_isaret2,
            false,
            "Bu hangi harftir?",
            listOf("J", "K", "L", "M"),
            3
        ),
        Question(
            R.raw.kardes,
            true,
            "Bu hangi kelimedir?",
            listOf("Baba", "Anne", "Ben", "Kardeş"),
            3
        ),
        Question(
            R.drawable.y_isaret2,
            false,
            "Bu hangi harftir?",
            listOf("P", "R", "Y", "S"),
            2
        ),
        Question(
            R.raw.tesekkur,
            true,
            "Bu hangi kelimedir?",
            listOf("Lütfen", "Teşekkür ederim", "İyi", "Okul"),
            1
        ),
        Question(
            R.drawable.s_isaret2,
            false,
            "Bu hangi harftir?",
            listOf("M", "R", "S", "V"),
            2
        ),
        Question(
            R.raw.hasta,
            true,
            "Bu hangi kelimedir?",
            listOf("Hasta", "Evet", "İyi", "Kötü"),
            0
        ),
        Question(
            R.drawable.h_isaret2,
            false,
            "Bu hangi harftir?",
            listOf("M", "H", "Ğ", "V"),
            1
        ),

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_choice)

        playerView = findViewById(R.id.playerView)
        imageView = findViewById(R.id.imageView)
        questionText = findViewById(R.id.questionText)

        buttons = listOf(
            findViewById(R.id.btn1),
            findViewById(R.id.btn2),
            findViewById(R.id.btn3),
            findViewById(R.id.btn4)
        )

        player = ExoPlayer.Builder(this).build()
        playerView.player = player

// 👇 Videoya basınca tekrar oynat
        playerView.setOnClickListener {
            player.seekTo(0)
            player.play()
        }

        loadQuestion()
    }

    private fun loadQuestion() {

        val question = questions[currentIndex]
        questionText.text = question.question

        // Medya göster
        if (question.isVideo) {
            imageView.visibility = View.GONE
            playerView.visibility = View.VISIBLE

            val uri = Uri.parse("android.resource://$packageName/${question.mediaResId}")
            val mediaItem = MediaItem.fromUri(uri)
            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()
        } else {
            player.stop()
            playerView.visibility = View.GONE
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(question.mediaResId)
        }

        // Şıkları ayarla
        buttons.forEachIndexed { index, button ->
            button.isEnabled = true
            button.setBackgroundColor(getColor(android.R.color.darker_gray))
            button.text = question.options[index]
            button.setOnClickListener {
                checkAnswer(index)
            }
        }
    }

    private fun checkAnswer(selectedIndex: Int) {

        val correctIndex = questions[currentIndex].correctIndex

        if (selectedIndex == correctIndex) {

            correctCount++

            Toast.makeText(this, "Doğru!", Toast.LENGTH_SHORT).show()

        } else {

            wrongCount++

            val correctAnswer = questions[currentIndex].options[correctIndex]

            Toast.makeText(
                this,
                "Yanlış! Doğru cevap: $correctAnswer",
                Toast.LENGTH_LONG
            ).show()
        }

        // 1 saniye sonra diğer soruya geç
        buttons[0].postDelayed({
            currentIndex++

            if (currentIndex < questions.size) {
                loadQuestion()
            } else {
                showResult()
            }
        }, 1000)
    }
    private fun showResult() {

        val total = correctCount + wrongCount
        val sharedPref = getSharedPreferences("stats", MODE_PRIVATE)
        val editor = sharedPref.edit()

        val totalCorrect = sharedPref.getInt("correct", 0)
        val totalWrong = sharedPref.getInt("wrong", 0)
        val totalTest = sharedPref.getInt("test", 0)

        editor.putInt("correct", totalCorrect + correctCount)
        editor.putInt("wrong", totalWrong + wrongCount)
        editor.putInt("test", totalTest + 1)

        editor.apply()

        val message = """
Test Bitti!

Toplam Soru: $total
Doğru: $correctCount
Yanlış: $wrongCount
""".trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Sonuçlar")
            .setMessage(message)
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