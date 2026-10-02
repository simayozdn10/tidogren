package com.example.tidogren

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LessonsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessons)

        val lesson1 = findViewById<Button>(R.id.btnLesson1) // Alfabe
        val lesson2 = findViewById<Button>(R.id.btnLesson2) // Temel Kelimeler
        val btnBack = findViewById<Button>(R.id.btnBackLessons)

        lesson1.setOnClickListener {
            val intent = Intent(this, AlphabetActivity::class.java)
            startActivity(intent)
        }

        // 🔥 ASIL İSTEDİĞİN KISIM
        lesson2.setOnClickListener {
            val intent = Intent(this, BasicWordsActivity::class.java)
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
