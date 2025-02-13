package com.ctrlz.beslim.view

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController

import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels

import androidx.lifecycle.Observer
import com.ctrlz.beslim.viewModel.MainViewModel

import com.ctrlz.beslim.R

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_layout)

        //LIGHT STATUS BAR ICONS (>=Android 11)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }

        //LIGHT STATUS BAR ICONS (<= Android 10)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            window.statusBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        val createAccountButton: Button = findViewById(R.id.create_account_button)
        createAccountButton.setOnClickListener {
            val signUpFragment = SignUpFragment()
            signUpFragment.show(supportFragmentManager, signUpFragment.tag)
        }

        val logLink = findViewById<TextView>(R.id.loginLink)
        logLink.setOnClickListener {
            viewModel.openBottomSheet()
        }

        viewModel.showBottomSheet.observe(this, Observer { shouldShow ->
            if (shouldShow) {
                val signInFragment = SignInFragment()
                signInFragment.show(supportFragmentManager, signInFragment.tag)
                viewModel.closeBottomSheet()
            }
        })


        val layout = findViewById<View>(R.id.main)
        val layerDrawable = layout.background as LayerDrawable
        val gradient = layerDrawable.getDrawable(0).mutate() as GradientDrawable

        val animator = ValueAnimator.ofFloat(0.40f, 0.6f).apply {
            duration = 5000
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener { animation ->
                val newRadius = animation.animatedValue as Float
                gradient.gradientRadius = layout.width * newRadius
                layout.invalidate()
            }
            start()
        }
    }
}
