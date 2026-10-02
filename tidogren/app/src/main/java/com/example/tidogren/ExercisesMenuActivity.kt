package com.example.tidogren

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class

ExercisesMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercises_menu)

        val btnMultiple = findViewById<Button>(R.id.btnMultipleChoice)
        val btnClassic = findViewById<Button>(R.id.btnClassic)
        val btnMatching = findViewById<Button>(R.id.btnMatching)
        val btnTrueFalse = findViewById<Button>(R.id.btnTrueFalse)

        btnMultiple.setOnClickListener {
            startActivity(Intent(this, MultipleChoiceActivity::class.java))
        }

        btnClassic.setOnClickListener {
            startActivity(Intent(this, ClassicExerciseActivity::class.java))
        }

        btnMatching.setOnClickListener {
            startActivity(Intent(this, MatchingExerciseActivity::class.java))
        }

        btnTrueFalse.setOnClickListener {
            startActivity(Intent(this, TrueFalseExerciseActivity::class.java))
        }
    }
}
