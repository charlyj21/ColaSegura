package com.charlyj21.colasegura

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity() {

    private val onBoardingPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            when (position){
                0 -> {
                    skipBtn.text = "Skip"
                    skipBtn.visible()
                    nextBtn.visible()
                    previousBtn.gone()
                }
                pagerList.size - 1 -> {
                    skipBtn.text = "Get Started"
                    skipBtn.visible()
                    nextBtn.visible()
                    previousBtn.visible()
                }
                else -> {
                    skipBtn.text = "Skip"
                    skipBtn.visible()
                    nextBtn.visible()
                    previousBtn.visible()
                }
            }
        }
    }

    private val pagerList = arrayListOf(
        Page(
            "Cola Segura",
            R.drawable.ic_onboarding_1,
            R.string.onboarding_desc_1,
            "#F2D6C2"
        ),
        Page(
            "Escanea el código QR",
            R.drawable.ic_onboarding_2,
            R.string.onboarding_desc_2,
            "#F2D6C2"
        ),
        Page(
            "Ingresa a la cola",
            R.drawable.ic_onboarding_3,
            R.string.onboarding_desc_3,
            "#F2D6C2"
        )
    )

    private lateinit var onBoardingViewPager2: ViewPager2
    lateinit var skipBtn: Button
    lateinit var nextBtn: Button
    lateinit var previousBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        onBoardingViewPager2 = findViewById(R.id.onBoardingViewPager2)
        skipBtn = findViewById(R.id.skipBtn)
        nextBtn = findViewById(R.id.nextBtn)
        previousBtn = findViewById(R.id.previousBtn)

        onBoardingViewPager2.apply {
            adapter = OnBoardingAdapter(this@OnBoardingActivity, pagerList)
            registerOnPageChangeCallback(onBoardingPageChangeCallback)
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tabLayout, onBoardingViewPager2) { tab, position -> }.attach()

        nextBtn.setOnClickListener {
            if (onBoardingViewPager2.currentItem < onBoardingViewPager2.adapter!!.itemCount - 1){
                onBoardingViewPager2.currentItem += 1
            } else {
                homeScreenIntent()
            }
        }

        previousBtn.setOnClickListener {
            if (onBoardingViewPager2.currentItem > 0){
                onBoardingViewPager2.currentItem -= 1
            }
        }

        skipBtn.setOnClickListener {
            homeScreenIntent()
        }

    }

    override fun onDestroy() {
        onBoardingViewPager2.unregisterOnPageChangeCallback(onBoardingPageChangeCallback)
        super.onDestroy()
    }

    private fun homeScreenIntent() {
        val sharedPreferenceManger = SharedPreferenceManger(this)
        sharedPreferenceManger.isFirstTime = false
        val homeIntent = Intent(this, MainActivity::class.java)
        startActivity(homeIntent)
        finish()
    }
}