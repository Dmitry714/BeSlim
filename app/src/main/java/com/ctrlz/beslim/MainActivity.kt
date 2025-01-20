package com.ctrlz.beslim

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ctrlz.beslim.model.User
import com.ctrlz.beslim.utils.ApiClient
import kotlinx.coroutines.launch

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

        // #Open register curtain

        val createAccountButton: Button = findViewById(R.id.create_account_button)
        createAccountButton.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)}

//        val createAccountButton: Button = findViewById(R.id.create_account_button)

//        createAccountButton.setOnClickListener {
//            lifecycleScope.launch {
//                try {
//
//                    val user = User(
//                        firstName = "Olezhka",
//                        lastName = "Pupkin",
//                        email = "ebitemenya@mail.ru",
//                        password = "bobik23",
//                        age = "43",
//                        weight = "150",
//                        height = "156"
//                    )
//
//                    val response = ApiClient.api.addUser(user)
//
//                    if (response.isSuccessful) {
//                        showDialog("Success", response.body()?.message ?: "Хз че произошло ._.")
//                    } else {
//                        showDialog(
//                            "Error:", response.errorBody()?.string() ?: "Хз че произошло ._."
//                        )
//                    }
//                } catch (e: Exception) {
//                    showDialog("API Exception:", e.message ?: "Хз че произошло ._.")
//                }
//            }
//        }
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
