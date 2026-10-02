package com.example.tidogren
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TrueFalseExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_true_false)

        val tvResult = findViewById<TextView>(R.id.tvResult)
        val btnTrue = findViewById<Button>(R.id.btnTrue)
        val btnFalse = findViewById<Button>(R.id.btnFalse)

        btnTrue.setOnClickListener {
            tvResult.text = "✅ Doğru"
        }

        btnFalse.setOnClickListener {
            tvResult.text = "❌ Yanlış"
        }
    }
}