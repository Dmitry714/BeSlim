package com.ctrlz.beslim.view

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ctrlz.beslim.R
import com.ctrlz.beslim.utils.StepCounter

class HomeActivity : AppCompatActivity() {

    private lateinit var stepCounter: StepCounter
    private lateinit var stepsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_layout)

        enableEdgeToEdge()

        stepsTextView = findViewById(R.id.stepCountTextView)

        stepCounter = StepCounter(this)


        stepCounter.stepsLiveData.observe(this, Observer { steps ->
            stepsTextView.text = "${steps.toInt()}"
        })
    }

    override fun onResume() {
        super.onResume()
        stepCounter.startListening()
    }

    override fun onPause() {
        super.onPause()
        stepCounter.stopListening()
    }
}
