package com.ctrlz.beslim.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ctrlz.beslim.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.auth_layout)

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
            val bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }

    private fun showDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton("Похуй") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }
}
