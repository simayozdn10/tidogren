package com.example.tidogren
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MultipleChoiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_choice)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val btnCheck = findViewById<Button>(R.id.btnCheck)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnCheck.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId

            if (selectedId == R.id.rbA) {
                tvResult.text = "✅ Doğru Cevap"
            } else {
                tvResult.text = "❌ Yanlış Cevap"
            }
        }
    }
}