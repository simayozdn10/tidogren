package com.example.tidogren

import android.content.Context // Context import'unu ekle
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUser = findViewById<EditText>(R.id.etLoginUsername)
        val etPass = findViewById<EditText>(R.id.etLoginPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvGoRegister = findViewById<TextView>(R.id.tvGoRegister)

        btnLogin.setOnClickListener {
            val userInput = etUser.text.toString().trim()
            val passInput = etPass.text.toString().trim()

            // ... (boş kontrolü gibi diğer kontroller)

            // SharedPreferences'ten kayıtlı bilgileri al
            val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val savedUsername = sharedPreferences.getString("USERNAME", null)
            val savedPassword = sharedPreferences.getString("PASSWORD", null)

            // Girilen bilgilerle kayıtlı bilgileri karşılaştır
            if (userInput == savedUsername && passInput == savedPassword) {
                // BİLGİLER DOĞRUYSA
                Toast.makeText(this, "Giriş başarılı", Toast.LENGTH_SHORT).show()

                // YENİ EKLENEN KISIM: HomeActivity'yi (Ana Ekran) başlat
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

                // Bu ekranı kapat ki kullanıcı geri tuşuyla login ekranına dönemesin
                finish()

            } else {
                // BİLGİLER YANLIŞSA
                Toast.makeText(this, "Bilgiler yanlış veya böyle bir kullanıcı yok", Toast.LENGTH_SHORT).show()
            }
        }

        tvGoRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
