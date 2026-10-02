package com.example.tidogren

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ExercisesMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercises_menu)

        // Butonları tanımlıyoruz
        val btnMultiple = findViewById<Button>(R.id.btnMultipleChoice)
        val btnClassic = findViewById<Button>(R.id.btnClassic)
        val btnMatching = findViewById<Button>(R.id.btnMatching)
        val btnTrueFalse = findViewById<Button>(R.id.btnTrueFalse)

        // Çoktan Seçmeli Test
        btnMultiple.setOnClickListener {
            startActivity(Intent(this, MultipleChoiceActivity::class.java))
        }

        // Klasik Egzersiz
        btnClassic.setOnClickListener {
            startActivity(Intent(this, ClassicExerciseActivity::class.java))
        }

        // Eşleştirme Oyunu
        btnMatching.setOnClickListener {
            startActivity(Intent(this, MatchingActivity::class.java))
        }

        // Doğru/Yanlış Oyunu - İsim Düzeltildi: TrueFalseActivity
        btnTrueFalse.setOnClickListener {
            val intent = Intent(this, TrueFalseActivity::class.java)
            startActivity(intent)
        }
    }
}