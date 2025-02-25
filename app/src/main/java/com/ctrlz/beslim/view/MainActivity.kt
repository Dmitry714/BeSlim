package com.ctrlz.beslim.view

import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import com.ctrlz.beslim.utils.AndroidUI

import com.ctrlz.beslim.R
import com.ctrlz.beslim.controls.LoadingButton
import com.ctrlz.beslim.controls.TestButton
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


        val singInLink = findViewById<TextView>(R.id.signInTextView)
        val signUpButton: Button = findViewById(R.id.singUpButton)
        val layout = findViewById<View>(R.id.main)
        val layerDrawable = layout.background as LayerDrawable
        val androidUI = AndroidUI()

        androidUI.setLightStatusBarIcons(this)
        androidUI.setTransparentNavigation(this)

        setupClickListeners(
            signUpButton,
            singInLink
        )

        backgroundAnimation(
            layout,
            layerDrawable
        )


//        val button = findViewById<LoadingButton>(R.id.loadingButton)
//
//        button.setOnClickListener {
//            button.startLoading()
//            handler.postDelayed({
//                button.stopLoading()
//            }, 3000)
//        }


        val button2 = findViewById<TestButton>(R.id.testButton)

        button2.setOnClickListener {
            button2.startLoading()

            CoroutineScope(Dispatchers.IO).launch {
                delay(2000)
                withContext(Dispatchers.Main) {
                    button2.stopLoading()
                }
            }
        }
    }




    private fun setupClickListeners(
        signUpButton: Button,
        signInLink: TextView
    ) {
        signUpButton.setOnClickListener {
            if (supportFragmentManager.findFragmentByTag("signUpFragment") == null) {
                val signUpFragment = SignUpFragment()
                signUpFragment.show(supportFragmentManager, "signUpFragment")
            }
        }

        signInLink.setOnClickListener {
            if (supportFragmentManager.findFragmentByTag("signInFragment") == null) {
                val signInFragment = SignInFragment()
                signInFragment.show(supportFragmentManager, "signInFragment")
            }
        }
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
