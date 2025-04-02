package com.ctrlz.beslim.controls

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ctrlz.beslim.R

class CustomButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val textView: TextView
    private val imageStart: ImageView
    private val imageEnd: ImageView
    private val rootLayout: ConstraintLayout
    private val progressBar: ProgressBar

    private var animator: ObjectAnimator? = null
    private var buttonText: String = ""
    private var imageStartSrc: Int = 0
    private var imageEndSrc: Int = 0
    private var backroundTint: Int = 0
    private var textSize: Float = 0f
    private var textColor: Int = 0
    private var fontFamily: Int = 0
    private var progressColor: Int = 0


    private var textMarginStart: Int = 0


    init {
        LayoutInflater.from(context).inflate(R.layout.custom_button_layout, this, true)

        textView = findViewById(R.id.buttonText)
        progressBar = findViewById(R.id.progressBar)
        rootLayout = findViewById(R.id.constraintLayout)
        imageStart = findViewById(R.id.imageStart)
        imageEnd = findViewById(R.id.imageEnd)

        attrs?.let {
            setupAttributes(it)
        }

        setupButtonText()
        setupBackgroundTint()
        setupImages()
        setupProgressBar()
        setupTextMarginStart(textMarginStart)

        setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    animatePress(true)
                    isPressed = true
                    return@setOnTouchListener true
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {

                    isPressed = false
                    postDelayed({ animatePress(false) }, 80)
                    performClick()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun setupAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TestButton, 0, 0)
        buttonText = typedArray.getString(R.styleable.TestButton_text) ?: ""
        textColor = typedArray.getColor(R.styleable.TestButton_textColor, 0)
        fontFamily = typedArray.getResourceId(R.styleable.TestButton_fontFamily, 0)
        imageStartSrc = typedArray.getResourceId(R.styleable.TestButton_imageStartSrc, 0)
        imageEndSrc = typedArray.getResourceId(R.styleable.TestButton_imageEndSrc, 0)
        backroundTint = typedArray.getResourceId(R.styleable.TestButton_background, 0)
        progressColor = typedArray.getResourceId(R.styleable.TestButton_progressColor, 0)
        textMarginStart = typedArray.getDimensionPixelSize(R.styleable.TestButton_textMarginStart, 0)

        textSize = typedArray.getDimension(
            R.styleable.TestButton_textSize,
            0f
        ) / resources.displayMetrics.scaledDensity
        typedArray.recycle()
    }

    private fun setupButtonText() {
        if (buttonText.isNotEmpty()) {
            textView.text = buttonText
        } else {
            textView.text = "Pressed = gay"
        }

        if (textSize != 0f) {
            textView.textSize = textSize
        }

        if (textColor != 0) {
            textView.setTextColor(textColor)
        }

        if (fontFamily != 0) {
            textView.typeface = context.resources.getFont(fontFamily)
        }
    }

    fun setupTextMarginStart(margin: Int) {
        textView?.post {
            val params = textView?.layoutParams as? ViewGroup.MarginLayoutParams
                ?: ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.marginStart = margin
            textView?.layoutParams = params
        }
    }

    private fun setupBackgroundTint() {
        if (backroundTint != 0) {
            val colorStateList = ColorStateList.valueOf(context.getColor(backroundTint))
            rootLayout.backgroundTintList = colorStateList
        }
    }

    private fun setupImages() {
        if (imageStartSrc != 0) {
            imageStart.setImageResource(imageStartSrc)
        }

        if (imageEndSrc != 0) {
            imageEnd.setImageResource(imageEndSrc)
        }
    }


    private fun setupProgressBar() {
        if (progressColor != 0) {
            progressBar.indeterminateTintList =
                ColorStateList.valueOf(context.getColor(progressColor))
        }
    }

    fun startLoading() {
        textView.visibility = GONE
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