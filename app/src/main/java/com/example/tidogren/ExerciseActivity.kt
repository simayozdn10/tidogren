package com.example.tidogren

import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class ExerciseActivity : AppCompatActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var questionText: TextView
    private lateinit var optionButtons: List<Button>
    private lateinit var progressBar: ProgressBar

    private var currentQuestionIndex = 0
    private var score = 0
    private var timer: CountDownTimer? = null

    data class Question(
        val mediaResId: Int,
        val question: String,
        val options: List<String>,
        val correctIndex: Int,
        val isVideo: Boolean
    )

    private val questions = listOf(
        Question(
            R.raw.aile,
            "Bu hangi kelimedir?",
            listOf("Anne", "Aile", "Baba", "Kardeş"),
            1,
            true
        ),
        Question(
            R.drawable.a_isaret,
            "Bu hangi harftir?",
            listOf("A", "B", "C", "D"),
            0,
            false
        )
    ).shuffled()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        playerView = findViewById(R.id.playerView)
        questionText = findViewById(R.id.questionText)
        progressBar = findViewById(R.id.progressBar)

        optionButtons = listOf(
            findViewById(R.id.btn1),
            findViewById(R.id.btn2),
            findViewById(R.id.btn3),
            findViewById(R.id.btn4)
        )

        player = ExoPlayer.Builder(this).build()
        playerView.player = player

        loadQuestion()
    }

    private fun loadQuestion() {

        timer?.cancel()

        val question = questions[currentQuestionIndex]
        questionText.text = question.question

        optionButtons.forEachIndexed { index, button ->
            button.isEnabled = true
            button.text = question.options[index]
            button.setBackgroundColor(getColor(android.R.color.darker_gray))
            button.setOnClickListener { checkAnswer(index) }
        }

        if (question.isVideo) {
            playerView.visibility = View.VISIBLE
            val uri = Uri.parse("android.resource://$packageName/${question.mediaResId}")
            val mediaItem = MediaItem.fromUri(uri)
            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()
        } else {
            playerView.visibility = View.GONE
            player.stop()
        }

        startTimer()
    }

    private fun checkAnswer(selectedIndex: Int) {
        val correctIndex = questions[currentQuestionIndex].correctIndex

        if (selectedIndex == correctIndex) {
            score++
            optionButtons[selectedIndex]
                .setBackgroundColor(getColor(android.R.color.holo_green_light))
        } else {
            optionButtons[selectedIndex]
                .setBackgroundColor(getColor(android.R.color.holo_red_light))

            optionButtons[correctIndex]
                .setBackgroundColor(getColor(android.R.color.holo_green_light))
        }

        timer?.cancel()
        optionButtons.forEach { it.isEnabled = false }

        optionButtons[0].postDelayed({
            nextQuestion()
        }, 1500)
    }

    private fun nextQuestion() {
        currentQuestionIndex++

        if (currentQuestionIndex < questions.size) {
            loadQuestion()
        } else {
            Toast.makeText(this, "Bitti! Skor: $score", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun startTimer() {
        progressBar.progress = 100

        timer = object : CountDownTimer(7000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                progressBar.progress = (millisUntilFinished / 70).toInt()
            }

            override fun onFinish() {
                nextQuestion()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}