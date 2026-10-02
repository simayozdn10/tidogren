package com.example.tidogren

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.Locale

class HomeActivity : AppCompatActivity() {

    private val letters = setOf(
        "a","b","c","ç","d","e","f","g","ğ","h","ı","i","j","k","l","m","n",
        "o","ö","p","r","s","ş","t","u","ü","v","y","z"
    )

    private lateinit var tvDailyWord: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val search = findViewById<AutoCompleteTextView>(R.id.etSearch)
        val btnSearch = findViewById<Button>(R.id.btnSearch)

        // 🔥 GÜNÜN KELİMESİ
        tvDailyWord = findViewById(R.id.tvDailyWord)

        val dailyWord = getDailyWord()
        tvDailyWord.text = "Günün kelimesi: $dailyWord"

        tvDailyWord.setOnClickListener {
            handleSearch(dailyWord)
        }

        findViewById<Button>(R.id.btnLessons).setOnClickListener {
            startActivity(Intent(this, LessonsActivity::class.java))
        }

        findViewById<Button>(R.id.btnExercises).setOnClickListener {
            startActivity(Intent(this, ExercisesMenuActivity::class.java))
        }

        findViewById<Button>(R.id.btnReports).setOnClickListener {
            startActivity(Intent(this, ReportsActivity::class.java))
        }

        findViewById<Button>(R.id.btnAbout).setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        val suggestions = listOf(
            *letters.toTypedArray(),
            "anne","baba","aile","okul","merhaba","su","teşekkür",
            "alfabe","alıştırma","rapor"
        )

        search.setAdapter(
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, suggestions)
        )

        search.setOnItemClickListener { _, _, position, _ ->
            val text = normalize(search.adapter.getItem(position).toString())
            handleSearch(text)
        }

        btnSearch.setOnClickListener {
            val text = normalize(search.text.toString())
            handleSearch(text)
        }
    }

    // 🔥 ANA NAVIGATION
    private fun handleSearch(text: String) {

        when {

            letters.contains(text) -> {
                startActivity(
                    Intent(this, AlphabetActivity::class.java).apply {
                        putExtra("letter", text)
                    }
                )
            }

            text == "alfabe" -> {
                startActivity(Intent(this, AlphabetActivity::class.java))
            }

            isBasicWord(text) -> {
                startActivity(
                    Intent(this, BasicWordsActivity::class.java).apply {
                        putExtra("word", text)
                    }
                )
            }

            text.contains("test") || text.contains("alıştırma") -> {
                startActivity(Intent(this, ExercisesMenuActivity::class.java))
            }

            text.contains("rapor") -> {
                startActivity(Intent(this, ReportsActivity::class.java))
            }

            else -> {
                Toast.makeText(this, "Sonuç bulunamadı", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 🔥 GÜNLÜK KELİME SİSTEMİ (SABİT + CACHE)
    private fun getDailyWord(): String {

        val words = listOf(
            "aile","akşam","anlamak","anne","baba","belki","ben","biz",
            "bugün","değil","dün","evet","günaydın",
            "hasta","hayır","iyi","kardeş","kötü","lütfen",
            "merhaba","öğretmen","okul","sen","su","susamak",
            "tekrar","teşekkür","yardım","yarın","yorgun"
        )

        val prefs = getSharedPreferences("daily_word", MODE_PRIVATE)

        val savedDay = prefs.getInt("day", -1)
        val savedWord = prefs.getString("word", null)

        val today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)

        // 🔥 aynı günse tekrar hesaplama
        if (savedDay == today && savedWord != null) {
            return savedWord
        }

        // 🔥 yeni gün → yeni kelime
        val newWord = words[today % words.size]

        prefs.edit()
            .putInt("day", today)
            .putString("word", newWord)
            .apply()

        return newWord
    }

    private fun normalize(input: String): String {
        return input.lowercase(Locale("tr", "TR")).trim()
    }

    private fun isBasicWord(word: String): Boolean {
        val words = setOf(
            "aile","akşam","anlamak","anne","baba","belki","ben","biz",
            "bugün","değil","dün","evet","günaydın",
            "hasta","hayır","iyi","kardeş","kötü","lütfen",
            "merhaba","öğretmen","okul","sen","su","susamak",
            "tekrar","teşekkür","yardım","yarın","yorgun"
        )
        return words.contains(word)
    }
}