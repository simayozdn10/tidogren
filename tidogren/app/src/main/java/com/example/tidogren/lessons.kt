package com.example.tidogren

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LessonsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessons)

        val lesson1 = findViewById<Button>(R.id.btnLesson1)
        val lesson2 = findViewById<Button>(R.id.btnLesson2)
        val lesson3 = findViewById<Button>(R.id.btnLesson3)
        val lesson4 = findViewById<Button>(R.id.btnLesson4)
        val btnBack = findViewById<Button>(R.id.btnBackLessons)

        // 🔤 ALFABE DERSİ
        lesson1.setOnClickListener {
            val intent = Intent(this, AlphabetActivity::class.java)
            startActivity(intent)
        }

        // 📘 DİĞER DERSLER (ŞİMDİLİK BİLGİ)
        lesson2.setOnClickListener {
            Toast.makeText(this, "Temel kelimeler dersi yakında", Toast.LENGTH_SHORT).show()
        }

        lesson3.setOnClickListener {
            Toast.makeText(this, "Günlük ifadeler dersi yakında", Toast.LENGTH_SHORT).show()
        }

        lesson4.setOnClickListener {
            Toast.makeText(this, "Cümleler dersi yakında", Toast.LENGTH_SHORT).show()
        }

        // ⬅️ GERİ
        btnBack.setOnClickListener {
            finish()
        }
    }
}
