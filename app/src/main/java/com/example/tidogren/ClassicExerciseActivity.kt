package com.example.tidogren

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class ClassicExerciseActivity : AppCompatActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var imageView: ImageView
    private lateinit var questionText: TextView
    private lateinit var answerInput: EditText
    private lateinit var checkButton: Button

    private var currentIndex = 0
    private var correctCount = 0
    private var wrongCount = 0

    // Veri sınıfın mevcut sorularınla uyumlu
    data class Question(
        val mediaResId: Int,
        val isVideo: Boolean,
        val question: String,
        val answer: String
    )

    private val questions = listOf(
        Question(R.raw.su, true, "Bu hangi kelimedir?", "su"),
        Question(R.raw.okul, true, "Bu hangi kelimedir?", "okul"),
        Question(R.drawable.b_isaret2, false, "Bu hangi harftir?", "b"),
        Question(R.drawable.k_isaret2, false, "Bu hangi harftir?", "k"),
        Question(R.raw.biz, true, "Bu hangi kelimedir?", "biz"),
        Question(R.raw.hayir, true, "Bu hangi kelimedir?", "hayır"),
        Question(R.drawable.z_isaret2, false, "Bu hangi harftir?", "z"),
        Question(R.raw.sen1, true, "Bu hangi kelimedir?", "sen"), // isVideo true yapıldı
        Question(R.drawable.u_isaret2, false, "Bu hangi harftir?", "u")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classic)

        playerView = findViewById(R.id.playerView)
        imageView = findViewById(R.id.imageView)
        questionText = findViewById(R.id.questionText)
        answerInput = findViewById(R.id.answerInput)
        checkButton = findViewById(R.id.checkButton)

        player = ExoPlayer.Builder(this).build()
        playerView.player = player

        // İlk soruyu başlat
        loadQuestion()

        playerView.setOnClickListener {
            player.seekTo(0)
            player.play()
        }

        checkButton.setOnClickListener {
            checkAnswer() // Parametresiz çağırıyoruz
            hideKeyboard()
        }
    }

    private fun loadQuestion() {
        answerInput.setText("")
        val question = questions[currentIndex]
        questionText.text = question.question

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
    }

    private fun checkAnswer() {
        val userAnswer = answerInput.text.toString().trim().lowercase()
        val correctAnswer = questions[currentIndex].answer.lowercase()

        if (userAnswer == correctAnswer) {
            correctCount++
            Toast.makeText(this, "Doğru!", Toast.LENGTH_SHORT).show()
        } else {
            wrongCount++
            Toast.makeText(this, "Yanlış! Cevap: $correctAnswer", Toast.LENGTH_SHORT).show()
        }

        currentIndex++

        if (currentIndex < questions.size) {
            loadQuestion()
        } else {
            showResult()
        }
    }

    private fun showResult() {
        val message = """
            Test Bitti!
            
            ✅ Doğru Sayısı: $correctCount
            ❌ Yanlış Sayısı: $wrongCount
            📈 Başarı: %${(correctCount * 100) / questions.size}
        """.trimIndent()
        val sharedPref = getSharedPreferences("stats", MODE_PRIVATE)
        val editor = sharedPref.edit()

        val totalCorrect = sharedPref.getInt("correct", 0)
        val totalWrong = sharedPref.getInt("wrong", 0)
        val totalTest = sharedPref.getInt("test", 0)

        editor.putInt("correct", totalCorrect + correctCount)
        editor.putInt("wrong", totalWrong + wrongCount)
        editor.putInt("test", totalTest + 1)

        editor.apply()

        AlertDialog.Builder(this)
            .setTitle("Sonuçlar")
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Tamam") { _, _ -> finish() }
            .show()
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}