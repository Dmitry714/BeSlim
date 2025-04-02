package com.ctrlz.beslim.view

import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import com.ctrlz.beslim.utils.AndroidUI

import com.ctrlz.beslim.R
import com.ctrlz.beslim.controls.CustomButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    // private val viewModel: MainViewModel by viewModels()
    private val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_layout)

        val signInLink: CustomButton = findViewById(R.id.signInButton)
        val signUpButton: CustomButton = findViewById(R.id.singUpButton)

        val layout = findViewById<View>(R.id.main)
        val layerDrawable = layout.background as LayerDrawable
        val androidUI = AndroidUI()

        androidUI.setLightStatusBarIcons(this)
        androidUI.setTransparentNavigation(this)

        signUpButton.setOnClickListener {

            signUpButton.startLoading()

            CoroutineScope(Dispatchers.IO).launch {
                delay(2000)
                withContext(Dispatchers.Main) {

                    if (supportFragmentManager.findFragmentByTag("signUpFragment") == null) {
                        val registerFragment = RegisterFragment()
                        registerFragment.show(supportFragmentManager, "signUpFragment")
                    }

                    signUpButton.stopLoading()
                }
            }
        }

        signInLink.setOnClickListener {
            if (supportFragmentManager.findFragmentByTag("signInFragment") == null) {
                val loginFragment = LoginFragment()
                loginFragment.show(supportFragmentManager, "signInFragment")
            }
        }

        backgroundAnimation(
            layout,
            layerDrawable
        )
    }

    private fun backgroundAnimation(
        layout: View,
        layerDrawable: LayerDrawable
    ) {
        val gradient = layerDrawable.getDrawable(3).mutate() as GradientDrawable
        val colorStart = getColor(R.color.orange)
        val colorTo = getColor(R.color.red)

        val animatorPulse = ValueAnimator.ofFloat(3.5f, 1.7f).apply {
            duration = 4000
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener { animation ->
                val newRadius = animation.animatedValue as Float
                gradient.gradientRadius = layout.width * newRadius

                layout.invalidate()
            }
            start()
        }

        val animatorChangeColor = ValueAnimator.ofArgb(colorStart, colorTo).apply {
            duration = 4000
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener { animation ->
                val newColor = animation.animatedValue as Int
                gradient.colors = intArrayOf(newColor, Color.TRANSPARENT, Color.TRANSPARENT)
                layout.invalidate()
            }
            start()
        }
    }
}
