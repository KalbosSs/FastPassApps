package com.example.fastpassapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class HomeFragment : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val btnNavigate = view.findViewById<Button>(R.id.btnNavigate)

        btnNavigate.setOnClickListener {
            navigateToFragmentB()
        }

        return view
    }

    private fun navigateToFragmentB() {
        val generateFragment = GenerateFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, generateFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}