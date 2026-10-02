package com.example.tidogren

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AlphabetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alphabet)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerAlphabet)
        recyclerView.layoutManager = LinearLayoutManager(this)

// 🔥 EKLE
        recyclerView.setHasFixedSize(false)
        recyclerView.itemAnimator = null
        recyclerView.isNestedScrollingEnabled = true

        val alphabetList = listOf(
            AlphabetItem("A", R.drawable.a_isaret),
            AlphabetItem("B", R.drawable.b_isaret),
            AlphabetItem("C", R.drawable.c_isaret),
            AlphabetItem("Ç", R.drawable.c_fingersnap_isaret),
            AlphabetItem("D", R.drawable.d_isaret) ,
            AlphabetItem("E", R.drawable.e_isaret),
            AlphabetItem("F", R.drawable.f_isaret),
            AlphabetItem("G", R.drawable.g_isaret),
            AlphabetItem("Ğ", R.drawable.g_fingersnap_isaret),
            AlphabetItem("H", R.drawable.h_isaret) ,
            AlphabetItem("I", R.drawable.i_nonefingersnap_isaret),
            AlphabetItem("İ", R.drawable.i_isaret),
            AlphabetItem("J", R.drawable.j_isaret),
            AlphabetItem("K", R.drawable.k_isaret),
            AlphabetItem("L", R.drawable.l_isaret),
            AlphabetItem("M", R.drawable.m_isaret),
            AlphabetItem("N", R.drawable.n_isaret),
            AlphabetItem("O", R.drawable.o_isaret),
            AlphabetItem("Ö", R.drawable.o_fingersnap_isaret),
            AlphabetItem("P", R.drawable.p_isaret),
            AlphabetItem("R", R.drawable.r_isaret),
            AlphabetItem("S", R.drawable.s_isaret),
            AlphabetItem("Ş", R.drawable.s_fingersnap_isaret),
            AlphabetItem("T", R.drawable.t_isaret),
            AlphabetItem("U", R.drawable.u_isaret),
            AlphabetItem("Ü", R.drawable.u_fingersnap_isaret),
            AlphabetItem("V", R.drawable.v_isaret),
            AlphabetItem("Y", R.drawable.y_isaret),
            AlphabetItem("Z", R.drawable.z_isaret),















        )

        recyclerView.adapter = AlphabetAdapter(alphabetList)
    }
}
