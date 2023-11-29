package com.example.fastpassapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val navSignup = findViewById<TextView>(R.id.navSignup)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        navSignup.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }

        btnLogin.setOnClickListener{
            logAccount()
        }
    }

    private fun logAccount() {
        val uname = findViewById<EditText>(R.id.etUsername).text.toString().trim()
        val pass = findViewById<EditText>(R.id.etPass).text.toString().trim()
        val db: DatabaseHandler = DatabaseHandler(this)
        if (uname.isNotEmpty() && pass.isNotEmpty()) {
            val isSuccess = db.logAccount(uname, pass)

            if (isSuccess) {
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.putExtra("USERNAME", uname)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Fill up everything!", Toast.LENGTH_LONG).show()
        }
    }

}