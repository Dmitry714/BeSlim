package com.ctrlz.beslim.controls

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.compose.ui.graphics.Color
import com.ctrlz.beslim.R

class CustomAppCompatButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val button = AppCompatButton(context, attrs, defStyleAttr).apply {
        gravity = Gravity.CENTER
        textAlignment = TEXT_ALIGNMENT_CENTER
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    private val progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleSmall).apply {
        visibility = View.GONE
        indeterminateTintList = ColorStateList.valueOf(context.getColor(R.color.white))

        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    init {

        backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.transparent))

        addView(button, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        addView(progressBar, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.CENTER
        })


        setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    animatePress(true)
                    return@setOnTouchListener true
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    animatePress(false)
                    performClick()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    override fun performClick(): Boolean{
        super.performClick()
        return true
    }

//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> animatePress(true)
//            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> postDelayed({ animatePress(false) }, 80)
//        }
//        return super.onTouchEvent(event)
//    }

    private fun animatePress(isPressed: Boolean) {
        val scale = if (isPressed) 0.95f else 1f
        button.animate().scaleX(scale).scaleY(scale).setDuration(150).start()
    }

    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
            button.text = ""
        } else {
            progressBar.visibility = View.GONE
            button.text = "Нажми меня"
        }
    }
}
