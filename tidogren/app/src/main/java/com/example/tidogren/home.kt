package com.example.tidogren

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 1. ADIM: Tüm bileşenleri en başta, bir kere tanımla
        val search = findViewById<EditText>(R.id.etSearch)
        val btnLessons = findViewById<Button>(R.id.btnLessons)
        val btnExercises = findViewById<Button>(R.id.btnExercises)
        val btnReports = findViewById<Button>(R.id.btnReports)
        val btnAbout = findViewById<Button>(R.id.btnAbout)

        // 2. ADIM: Her buton için tıklama olayını ayrı ayrı tanımla

        // Dersler Butonu
        btnLessons.setOnClickListener {
            startActivity(Intent(this, LessonsActivity::class.java))
        }

        // Alıştırmalar Butonu
        btnExercises.setOnClickListener {
            startActivity(Intent(this, ExercisesMenuActivity::class.java))
        }

        // Raporlar Butonu
        btnReports.setOnClickListener {
            Toast.makeText(this, "Raporlar açılacak", Toast.LENGTH_SHORT).show()
        }

        // Hakkında Butonu
        btnAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }
}
