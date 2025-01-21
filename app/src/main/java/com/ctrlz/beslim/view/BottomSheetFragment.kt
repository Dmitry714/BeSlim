package com.ctrlz.beslim.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.ctrlz.beslim.R
import com.ctrlz.beslim.model.User
import com.ctrlz.beslim.utils.ApiClient
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class BottomSheetFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val curtainView = inflater.inflate(R.layout.bottom_sheet_layout, container, false)

        val regButton: Button = curtainView.findViewById(R.id.continueButton)

        regButton.setOnClickListener {
            lifecycleScope.launch {
                try {

                    val emailValue: String =
                        curtainView.findViewById<EditText>(R.id.emailField).text.toString()
                    val passwordValue: String =
                        curtainView.findViewById<EditText>(R.id.passwordField).text.toString()
                    val confPassValue: String =
                        curtainView.findViewById<EditText>(R.id.confPasswordField).text.toString()

                    val user = User(
                        firstName = "null",
                        lastName = "null",
                        email = emailValue,
                        password = passwordValue,
                        age = "null",
                        weight = "null",
                        height = "null"
                    )

                    if (passwordValue == confPassValue) {
                        val response = ApiClient.api.addUser(user)

                        if (response.isSuccessful) {
                            showDialog("Success", response.body()?.message ?: "Хз че произошло ._.")
                            dismiss()
                        } else {
                            showDialog(
                                "Error:", response.errorBody()?.string() ?: "Хз че произошло ._."
                            )
                            dismiss()
                        }
                    } else {
                        showDialog("Errorchiks", "Пароли не совпадают! Зайка, попробуй еще раз :3")
                    }


                } catch (e: Exception) {
                    showDialog("API Exception:", e.message ?: "Хз че произошло ._.")
                }
            }
        }


        return curtainView
    }

    private fun showDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton("Похуй") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }
}


