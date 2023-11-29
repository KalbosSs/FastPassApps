package com.example.fastpassapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this, "Welcome to FastPASS!", Toast.LENGTH_LONG).show();
        Log.i("info","In onCreate")

        Handler().postDelayed({
            // Start LoginActivity after 3 seconds
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Optional: finish the current activity to prevent the user from coming back to it
        }, 3000)
    }
}