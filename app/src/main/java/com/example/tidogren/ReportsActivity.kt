package com.example.tidogren

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReportsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)

        val tvStats = findViewById<TextView>(R.id.tvStats)

        val sharedPref = getSharedPreferences("stats", MODE_PRIVATE)

        val correct = sharedPref.getInt("correct", 0)
        val wrong = sharedPref.getInt("wrong", 0)

        val total = correct + wrong

        val successRate = if (total > 0) {
            (correct.toFloat() / total) * 100
        } else 0f

        tvStats.text = """
📊 RAPORLAR

✔ Doğru: $correct
❌ Yanlış: $wrong

🏆 Başarı Oranı: %${"%.1f".format(successRate)}
        """.trimIndent()
    }
}