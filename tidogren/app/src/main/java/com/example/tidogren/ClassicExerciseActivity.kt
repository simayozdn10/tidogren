package com.example.tidogren

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ClassicExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classic)

        val etAnswer = findViewById<EditText>(R.id.etAnswer)
        val btnCheck = findViewById<Button>(R.id.btnCheck)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnCheck.setOnClickListener {
            val userAnswer = etAnswer.text.toString().trim().lowercase()

            if (userAnswer == "a") {
                tvResult.text = "✅ Doğru"
            } else {
                tvResult.text = "❌ Yanlış"
            }
        }
    }
}
