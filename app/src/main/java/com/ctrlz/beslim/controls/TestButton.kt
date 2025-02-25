package com.ctrlz.beslim.controls

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.ctrlz.beslim.R

class TestButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val textView: TextView
    private val progressBar: ProgressBar
    private var animator: ObjectAnimator? = null
    private var buttonText: String = ""

    init{
        LayoutInflater.from(context).inflate(R.layout.test_button_layout, this, true)
        textView = findViewById(R.id.buttonText)
        progressBar = findViewById(R.id.progressBar)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.TestButton, 0, 0)
            buttonText = typedArray.getString(R.styleable.TestButton_text) ?: ""
            typedArray.recycle()
        }

        textView.text = buttonText
        progressBar.visibility = GONE

        setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> animatePress(true)
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    animatePress(false)
                    performClick()
                }
            }
            false
        }
    }

    fun startLoading() {
        textView.visibility = GONE
        progressBar.visibility = VISIBLE
        animator = ObjectAnimator.ofFloat(progressBar, "rotation", 0f, 360f).apply {

            textView.visibility = GONE
            progressBar.visibility = VISIBLE
            animator = ObjectAnimator.ofFloat(progressBar, "rotation", 0f, 360f).apply {width
                duration = 1000
                repeatCount = ObjectAnimator.INFINITE
                interpolator = LinearInterpolator()
                start()
            }
        }
    }

    fun stopLoading(){
        animator?.cancel()
        progressBar.visibility = GONE
        textView.visibility = VISIBLE
    }

    private fun animatePress(isPressed: Boolean) {
        val scale = if (isPressed) 0.95f else 1f
        animate().scaleX(scale).scaleY(scale).setDuration(150).start()
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}