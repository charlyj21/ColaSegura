package com.charlyj21.colasegura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.slider.Slider
import kotlin.math.roundToInt

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val slider = root.findViewById<Slider>(R.id.slider1)
        val drawable = resources.getDrawable(R.drawable.huella)
        slider.setCustomThumbDrawable(drawable)

        // Actualizar el texto del TextView al iniciar la aplicación
        val initialValue = slider.getValue()
        updateTextView(initialValue, root)

        // Update text view when slider value changes
        slider.addOnChangeListener { _, value, _ ->
            updateTextView(value, root)
        }

        return root
    }

    private fun updateTextView(value: Float, root: View) {
        val textView = root.findViewById<TextView>(R.id.title2)
        val intValue = value.roundToInt() // Convierte a Int
        textView.text = String.format("%dm", intValue) // Format the value with "m"

        val imageView = root.findViewById<ImageView>(R.id.imageView)

        // Calculate image resource based on slider value
        val imageId = when (intValue) {
            10 -> R.drawable.toma1
            40 -> R.drawable.toma2
            70 -> R.drawable.toma3
            else -> R.drawable.huella
        }

        imageView.setImageResource(imageId) // Update image
    }
}