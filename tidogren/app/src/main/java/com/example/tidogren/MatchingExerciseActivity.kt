package com.example.tidogren
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MatchingExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching)

        val spinner = findViewById<Spinner>(R.id.spinner)
        val btnCheck = findViewById<Button>(R.id.btnCheck)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        val options = listOf("İşaret B", "İşaret C", "İşaret A")
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            options
        )

        btnCheck.setOnClickListener {
            if (spinner.selectedItem == "İşaret A") {
                tvResult.text = "✅ Doğru Eşleştirme"
            } else {
                tvResult.text = "❌ Yanlış Eşleştirme"
            }
        }
    }
}
