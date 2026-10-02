package com.example.tidogren

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register) // activity_register.xml dosyan olduğunu varsayıyorum

        val etRegisterUser = findViewById<EditText>(R.id.etRegUsername) // XML'deki ID'lerinle eşleştir
        val etRegisterPass = findViewById<EditText>(R.id.etRegPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val username = etRegisterUser.text.toString().trim()
            val password = etRegisterPass.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Kullanıcı adı ve şifre boş olamaz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 1. SharedPreferences dosyasını aç (veya oluştur)
            val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            // 2. Kullanıcı adı ve şifreyi kaydet
            editor.putString("USERNAME", username)
            editor.putString("PASSWORD", password)
            editor.apply() // Değişiklikleri uygula

            Toast.makeText(this, "Kayıt başarılı!", Toast.LENGTH_SHORT).show()

            // 3. Kayıttan sonra login ekranına geri dön
            finish() // RegisterActivity'yi kapat
        }
    }
}
