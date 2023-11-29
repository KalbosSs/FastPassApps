package com.example.fastpassapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun saveRecord(view: View) {
        val u_email = findViewById<EditText>(R.id.etEmail)
        val u_fname = findViewById<EditText>(R.id.etFname)
        val u_lname = findViewById<EditText>(R.id.etLname)
        val u_uname = findViewById<EditText>(R.id.etUsername)
        val u_pass = findViewById<EditText>(R.id.etPass)
        val feedback = findViewById<TextView>(R.id.tvPassLack)

        val email = u_email.text.toString()
        val fname = u_fname.text.toString()
        val lname = u_lname.text.toString()
        val uname = u_uname.text.toString()
        val pass = u_pass.text.toString()

        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        if (email.trim() != "" && fname.trim() != "" && lname.trim() != "" && uname.trim() != "" && pass.trim() != "") {

            val status = databaseHandler.regAccount(User(email, fname, lname, uname, pass))
            if (pass.length >= 8 && Regex(".*[0-9].*").containsMatchIn(pass)
                && Regex(".*[A-Z].*").containsMatchIn(pass)
                && Regex(".*[!.@#$%&*()_+=|<>?{}\\\\[\\\\]~-].*").containsMatchIn(pass)){
                Toast.makeText(this, "Record Saved", Toast.LENGTH_LONG).show()
                u_email.text.clear()
                u_fname.text.clear()
                u_lname.text.clear()
                u_uname.text.clear()
                u_pass.text.clear()
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }
            else {
                feedback.text = "Must contain lowercase, uppercase, number and special character. Minimum of 8 characters"
                feedback.setTextColor(Color.RED)
            }
        }
        else {
            Toast.makeText(this, "Fill up everything!", Toast.LENGTH_LONG)
                .show()
            feedback.text = ""
        }
    }
}