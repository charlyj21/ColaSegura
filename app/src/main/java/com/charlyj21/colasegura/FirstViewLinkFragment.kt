package com.charlyj21.colasegura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class FirstViewLinkFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_first_view_link, container, false)

        val btnLink1 = root.findViewById<Button>(R.id.btnLink1)

        btnLink1.setOnClickListener {
            findNavController().navigate(R.id.action_firstViewLinkFragment_to_secondViewLinkFragment)
        }

        return root
    }
}