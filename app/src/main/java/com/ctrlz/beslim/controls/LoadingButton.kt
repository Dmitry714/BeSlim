package com.ctrlz.beslim.controls

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton

class LoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

    private val progressBar: ProgressBar = ProgressBar(context).apply {
        layoutParams = FrameLayout.LayoutParams(40, 40).apply {
            gravity = android.view.Gravity.CENTER
        }
        visibility = GONE
    }

    private var originalText: String? = null
    private var animator: ObjectAnimator? = null

    init {
        (parent as? FrameLayout)?.addView(progressBar)


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

        setOnClickListener {
            startLoading()
        }

    }

    fun startLoading() {
        if (progressBar.parent == null) {
            (parent as? FrameLayout)?.addView(progressBar)
        }

        originalText = text.toString()
        text = ""
        isEnabled = false
        progressBar.visibility = VISIBLE

        animator = ObjectAnimator.ofFloat(progressBar, "rotation", 0f, 360f).apply {
            duration = 1000
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
            start()
        }
    }

    fun stopLoading() {
        animator?.cancel()
        progressBar.visibility = GONE
        text = originalText
        isEnabled = true
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
