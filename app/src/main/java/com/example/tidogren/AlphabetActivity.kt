package com.example.tidogren

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class AlphabetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alphabet)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerAlphabet)
        val imageView = findViewById<ImageView>(R.id.imageFull) // 🔥 EKLE

        val letter = intent.getStringExtra("letter")
            ?.lowercase(Locale("tr", "TR"))
            ?.trim()

        // 🔥 HARF GELDİYSE → FULL EKRAN
        if (!letter.isNullOrEmpty()) {

            val map = mapOf(
                "a" to R.drawable.a_isaret,
                "b" to R.drawable.b_isaret,
                "c" to R.drawable.c_isaret,
                "ç" to R.drawable.c_fingersnap_isaret,
                "d" to R.drawable.d_isaret,
                "e" to R.drawable.e_isaret,
                "f" to R.drawable.f_isaret,
                "g" to R.drawable.g_isaret,
                "ğ" to R.drawable.g_fingersnap_isaret,
                "h" to R.drawable.h_isaret,
                "ı" to R.drawable.i_nonefingersnap_isaret,
                "i" to R.drawable.i_isaret,
                "j" to R.drawable.j_isaret,
                "k" to R.drawable.k_isaret,
                "l" to R.drawable.l_isaret,
                "m" to R.drawable.m_isaret,
                "n" to R.drawable.n_isaret,
                "o" to R.drawable.o_isaret,
                "ö" to R.drawable.o_fingersnap_isaret,
                "p" to R.drawable.p_isaret,
                "r" to R.drawable.r_isaret,
                "s" to R.drawable.s_isaret,
                "ş" to R.drawable.s_fingersnap_isaret,
                "t" to R.drawable.t_isaret,
                "u" to R.drawable.u_isaret,
                "ü" to R.drawable.u_fingersnap_isaret,
                "v" to R.drawable.v_isaret,
                "y" to R.drawable.y_isaret,
                "z" to R.drawable.z_isaret
            )

            val resId = map[letter]

            if (resId != null) {
                recyclerView.visibility = View.GONE
                imageView.visibility = View.VISIBLE
                imageView.setImageResource(resId)
                return
            }
        }

        // 🔥 NORMAL LİSTE
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(false)
        recyclerView.itemAnimator = null
        recyclerView.isNestedScrollingEnabled = true

        val alphabetList = listOf(
            AlphabetItem(R.drawable.a_isaret),
            AlphabetItem(R.drawable.b_isaret),
            AlphabetItem(R.drawable.c_isaret),
            AlphabetItem(R.drawable.c_fingersnap_isaret),
            AlphabetItem(R.drawable.d_isaret),
            AlphabetItem(R.drawable.e_isaret),
            AlphabetItem(R.drawable.f_isaret),
            AlphabetItem(R.drawable.g_isaret),
            AlphabetItem(R.drawable.g_fingersnap_isaret),
            AlphabetItem(R.drawable.h_isaret),
            AlphabetItem(R.drawable.i_nonefingersnap_isaret),
            AlphabetItem(R.drawable.i_isaret),
            AlphabetItem(R.drawable.j_isaret),
            AlphabetItem(R.drawable.k_isaret),
            AlphabetItem(R.drawable.l_isaret),
            AlphabetItem(R.drawable.m_isaret),
            AlphabetItem(R.drawable.n_isaret),
            AlphabetItem(R.drawable.o_isaret),
            AlphabetItem(R.drawable.o_fingersnap_isaret),
            AlphabetItem(R.drawable.p_isaret),
            AlphabetItem(R.drawable.r_isaret),
            AlphabetItem(R.drawable.s_isaret),
            AlphabetItem(R.drawable.s_fingersnap_isaret),
            AlphabetItem(R.drawable.t_isaret),
            AlphabetItem(R.drawable.u_isaret),
            AlphabetItem(R.drawable.u_fingersnap_isaret),
            AlphabetItem(R.drawable.v_isaret),
            AlphabetItem(R.drawable.y_isaret),
            AlphabetItem(R.drawable.z_isaret)
        )

        recyclerView.adapter = AlphabetAdapter(alphabetList)
    }
}