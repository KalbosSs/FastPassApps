package com.example.fastpassapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

import androidx.fragment.app.Fragment
import java.util.Random

class GenerateFragment : Fragment() {

    private lateinit var editText: EditText
    private lateinit var databaseHandler: DatabaseHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_generate, container, false)

        editText = view.findViewById<EditText>(R.id.etPassword)
        val btnCopy = view.findViewById<ImageButton>(R.id.btnCopy)
        val btnCheck = view.findViewById<Button>(R.id.btnCheck)
        val btnGenerate = view.findViewById<Button>(R.id.btnGenerate)


        btnCopy.setOnClickListener {
            val textToCopy = editText.text.toString()
            copyToClipboard(textToCopy)
        }

        btnGenerate.setOnClickListener {
            val passValue = editText
            passValue.setText(GeneratePassword.randomString(15))

            databaseHandler = DatabaseHandler(requireContext())

            val intent: Intent? = activity?.intent

            if (intent != null) {
                val uname: String? = intent.getStringExtra("USERNAME")
                if (uname != null) {
                    val user = databaseHandler.getUserDetails(uname)

                    if (user != null) {
                        val email = "${user.email}"
                        val generatedPassword = passValue.text.toString()
                        databaseHandler.saveGeneratedPassword(email, generatedPassword)
                    }
                }
            }
        }

        btnCheck.setOnClickListener {
            val passValue = editText
            val input = passValue.editableText.toString()
            val feedback: TextView= view.findViewById(R.id.tvResult)
            val feedback1: TextView=view.findViewById(R.id.tvSuggest)

            if (input.isEmpty())
            {
                feedback.text = "There is no input"
                feedback1.text = ""
                feedback.setTextColor(Color.BLACK)

            }
            //Very Strong Passwords
            else if (input.length >= 8 && Regex(".*[0-9].*").containsMatchIn(input)
                && Regex(".*[A-Z].*").containsMatchIn(input)
                && Regex(".*[a-z].*").containsMatchIn(input)
                && Regex(".*[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].*").containsMatchIn(input))
            {
                // If password is 8 characters or more, has numbers, has uppercase, has special characters, and has lowercase
                feedback.text = "Very Strong"
                feedback.setTextColor(Color.parseColor("#006400"))
                feedback1.text = ""

            }

            //Strong Passwords
            else if (input.length >= 8 && Regex(".*[0-9].*").containsMatchIn(input) && Regex(".*[A-Z].*").containsMatchIn(input) && Regex(".*[a-z].*").containsMatchIn(input))
            {
                // If password is 8 characters or more, and has any three of the following: has numbers, has uppercase, and has lowercase
                feedback.text = "Strong"
                feedback.setTextColor(Color.parseColor("#006400"))
                feedback1.text = "Special Character"
            }

            else if  (input.length >= 8 && Regex(".*[A-Z].*").containsMatchIn(input) && Regex(".*[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].*").containsMatchIn(input) && Regex(".*[a-z].*").containsMatchIn(input))
            {
                // If password is 8 characters or more, and has any three of the following: has special characters, has uppercase, and has lowercase
                feedback.text = "Strong"
                feedback.setTextColor(Color.parseColor("#006400"))
                feedback1.text = "Numbers"
            }

            else if (input.length >= 8 && Regex(".*[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].*").containsMatchIn(input) && Regex(".*[0-9].*").containsMatchIn(input) && Regex(".*[a-z].*").containsMatchIn(input))
            {
                // If password is 8 characters or more, and has any three of the following: has numbers, has lowercase, and has special characters
                feedback.text = "Strong"
                feedback.setTextColor(Color.parseColor("#006400"))
                feedback1.text = "Uppercase Letters"
            }

            else if (input.length >= 8 && Regex(".*[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].*").containsMatchIn(input) && Regex(".*[0-9].*").containsMatchIn(input) && Regex(".*[A-Z].*").containsMatchIn(input))
            {
                // If password is 8 characters or more, and has any three of the following: has numbers, has uppercase, and has special characters
                feedback.text = "Strong"
                feedback.setTextColor(Color.parseColor("#006400"))
                feedback1.text = "Lowercase Letters"
            }

            //Medium Strength Passwords
            else if (input.length >= 8 && Regex(".*[A-Z].*").containsMatchIn(input) && Regex(".*[a-z].*").containsMatchIn(input))
            {
                // If password is 8 characters or more, and has any two of the following: has uppercase and has lowercase
                feedback.text = "Medium"
                feedback.setTextColor(Color.parseColor("#B58B00"))
                feedback1.text = "Numbers, Special Characters"
            }

            else if (input.length >= 8 && Regex(".*[0-9].*").containsMatchIn(input) && Regex(".*[a-z].*").containsMatchIn(input))
            {
                // If password is 8 characters or more, and has any two of the following: has number and has lowercase
                feedback.text = "Medium"
                feedback.setTextColor(Color.parseColor("#B58B00"))
                feedback1.text = "Uppercase Letters, Special Characters"
            }

            else if (input.length >= 8 && Regex(".*[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].*").containsMatchIn(input) && Regex(".*[a-z].*").containsMatchIn(input))
            {
                // If password is 8 characters or more, and has any two of the following: has special character and has lowercase
                feedback.text = "Medium"
                feedback.setTextColor(Color.parseColor("#B58B00"))
                feedback1.text = "Uppercase Letters, Numbers"
            }

            else if (input.length >= 8 && Regex(".*[A-Z].*").containsMatchIn(input) && Regex(".*[0-9].*").containsMatchIn(input))
            {
                // If password is 8 characters or more, and has any two of the following: has uppercase and has number
                feedback.text = "Medium"
                feedback.setTextColor(Color.parseColor("#B58B00"))
                feedback1.text = "Lowercase Letters, Special Characters"
            }

            else if (input.length >= 8 && Regex(".*[A-Z].*").containsMatchIn(input) && Regex(".*[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].*").containsMatchIn(input))
            {
                // If password is 8 characters or more, and has any two of the following: has uppercase and has special character
                feedback.text = "Medium"
                feedback.setTextColor(Color.parseColor("#B58B00"))
                feedback1.text = "Lowercase Letters, Numbers"
            }

            else if (input.length >= 8 && Regex(".*[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].*").containsMatchIn(input) && Regex(".*[0-9].*").containsMatchIn(input))
            {
                // If password is 8 characters or more, and has any two of the following: has special character and has number
                feedback.text = "Medium"
                feedback.setTextColor(Color.parseColor("#B58B00"))
                feedback1.text = "Uppercase Letters, Lowercase Letters"
            }

            //Weak Passwords
            else if (input.length >= 8 && Regex(".*[a-z].*").containsMatchIn(input))
            {
                // If password is 8 characters or more
                feedback.text = "Weak"
                feedback.setTextColor(Color.RED)
                feedback1.text = "Uppercase Letters, Numbers, Special Characters"
            }
            else if (input.length >= 8 && Regex(".*[A-Z].*").containsMatchIn(input))
            {
                // If password is 8 characters or more and has capitalization
                feedback.text = "Weak"
                feedback.setTextColor(Color.RED)
                feedback1.text = "Numbers, Special Characters, Lowercase Letters"
            }

            else if (input.length >= 8 && Regex(".*[0-9].*").containsMatchIn(input))
            {
                // If password is 8 characters or more and has numbers
                feedback.text = "Weak"
                feedback.setTextColor(Color.RED)
                feedback1.text = "Uppercase Letters, Special Characters, Lowercase Letters"
            }

            else if (input.length >= 8 && Regex(".*[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].*").containsMatchIn(input))
            {
                // If password is 8 characters or more and has unique characters
                feedback.text = "Weak"
                feedback.setTextColor(Color.RED)
                feedback1.text = "Uppercase Letters, Numbers, Lowercase Letters"
            }

            else if (input.length <= 7)
            {
                // If password is 7 characters or less
                feedback.text = "Weak"
                feedback.setTextColor(Color.RED)
                feedback1.text = "Too Short"
            }
        }
        return view
    }

    private fun copyToClipboard(text: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(requireContext(), "Text copied to clipboard", Toast.LENGTH_SHORT).show()
    }


    class GeneratePassword {
        companion object {
            const val DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!$%&@#_"
            val RANDOM = Random()

            fun randomString(len: Int): String {
                val sb = StringBuilder(len)

                for (i in 0 until len) {
                    sb.append(DATA[RANDOM.nextInt(DATA.length)])
                }
                return sb.toString()
            }
        }
    }


}
