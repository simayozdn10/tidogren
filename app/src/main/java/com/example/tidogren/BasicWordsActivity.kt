package com.example.tidogren

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import java.util.Locale

class BasicWordsActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    private val videoList = listOf(
        Pair(R.raw.aile, "Aile"),
        Pair(R.raw.aksam, "Akşam"),
        Pair(R.raw.anlamak, "Anlamak"),
        Pair(R.raw.anne, "Anne"),
        Pair(R.raw.baba, "Baba"),
        Pair(R.raw.belki, "Belki"),
        Pair(R.raw.ben1, "Ben"),
        Pair(R.raw.biz, "Biz"),
        Pair(R.raw.bugun, "Bugün"),
        Pair(R.raw.degil, "Değil"),
        Pair(R.raw.dun, "Dün"),
        Pair(R.raw.evet1, "Evet"),
        Pair(R.raw.gunaydin, "Günaydın"),
        Pair(R.raw.hasta, "Hasta"),
        Pair(R.raw.hayir, "Hayır"),
        Pair(R.raw.iyi, "İyi"),
        Pair(R.raw.kardes, "Kardeş"),
        Pair(R.raw.kotu, "Kötü"),
        Pair(R.raw.lutfen, "Lütfen"),
        Pair(R.raw.merhaba, "Merhaba"),
        Pair(R.raw.ogretmen1, "Öğretmen"),
        Pair(R.raw.okul, "Okul"),
        Pair(R.raw.sen1, "Sen"),
        Pair(R.raw.su, "Su"),
        Pair(R.raw.susamak, "Susamak"),
        Pair(R.raw.tekrar, "Tekrar"),
        Pair(R.raw.tesekkur, "Teşekkürler"),
        Pair(R.raw.yardim, "Yardım"),
        Pair(R.raw.yarin, "Yarın"),
        Pair(R.raw.yorgun, "Yorgun")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_words)

        viewPager = findViewById(R.id.viewPager)
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        viewPager.offscreenPageLimit = 1

        viewPager.adapter = VideoAdapter(videoList)

        // 🔥 KRİTİK DÜZELTME
        val word = intent.getStringExtra("word")?.lowercase(Locale("tr", "TR"))?.trim()

        if (!word.isNullOrEmpty()) {

            val index = videoList.indexOfFirst {
                it.second.lowercase(Locale("tr", "TR")).trim() == word
            }

            if (index != -1) {
                viewPager.post {
                    viewPager.setCurrentItem(index, false)
                }
            }
        }
    }
}