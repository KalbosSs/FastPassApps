package com.example.fastpassapp

import PasswordAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryFragment : Fragment() {

    private lateinit var passwordListView: ListView
    private lateinit var dbHandler: DatabaseHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        dbHandler = DatabaseHandler(requireContext())
        val intent: Intent? = activity?.intent
        if (intent != null) {
            val uname: String? = intent.getStringExtra("USERNAME")

            if (uname != null) {
                val user = dbHandler.getUserDetails(uname)

                if (user != null) {
                    val email = "${user.email}"
                    val passwordList = dbHandler.getLast20GeneratedPasswords(email)
                    val adapter = PasswordAdapter(requireContext(), R.layout.item_password, passwordList)
                    passwordListView.adapter = adapter
                }
            }
        }
        return view
    }
}