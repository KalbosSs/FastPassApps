package com.example.fastpassapp

import PasswordAdapter
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.system.exitProcess

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        val databaseHandler: DatabaseHandler = DatabaseHandler(requireContext())
        val intent: Intent? = activity?.intent
        val btnClearHistory = view.findViewById<Button>(R.id.btnClearHistory)

        btnClearHistory.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder
                .setTitle("Clear History")
                .setMessage("Are you sure you want to clear history?")
                .setPositiveButton("Yes") {dialog, id ->
                    databaseHandler.truncateDatabase()
                    Toast.makeText(requireContext(), "History Cleared", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") {dialog, id ->
                    dialog.dismiss()
                }
                .show()
        }

        if (intent != null) {
            val uname: String? = intent.getStringExtra("USERNAME")

            if (uname != null) {
                val user = databaseHandler.getUserDetails(uname)

                if (user != null) {
                    val email = "${user.email}"
                    val emp: List<EmpModelClass>? = databaseHandler.getLast20GeneratedPasswords(email)

                    if (emp != null) {
                        val empArrayPass = Array<String>(emp.size){"null"}
                        var index = 0

                        for(e in emp){
                            empArrayPass[index] = e.generatedPassword
                            index++
                        }

                        val listView = view.findViewById<ListView>(R.id.passwordListView)
                        val myListAdapter = PasswordAdapter(requireActivity(), empArrayPass)
                        listView.adapter = myListAdapter
                    }
                }
            }
        }
        return view
    }

}