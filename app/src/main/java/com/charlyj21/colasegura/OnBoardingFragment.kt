package com.charlyj21.colasegura

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class OnBoardingFragment(val page: Page) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_on_boarding, container, false)
        view.setBackgroundColor(Color.parseColor(page.color))
        val titleTxt = view.findViewById<TextView>(R.id.titleTxt)
        val descTxt = view.findViewById<TextView>(R.id.descTxt)
        val imageView = view.findViewById<ImageView>(R.id.imageView)

        titleTxt.text = page.title
        descTxt.text = resources.getString(page.desc)
        imageView.setImageResource(page.image)

        // Set font size
        if (page.fontSize != null) {
            titleTxt.textSize = page.fontSize
        }

        // Set marginTop
        if (page.imgMarginTop != null) {
            val params = imageView.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = page.imgMarginTop
            imageView.layoutParams = params
        }

        return view
    }
}