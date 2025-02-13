package com.ctrlz.beslim.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.ctrlz.beslim.R
import com.ctrlz.beslim.model.DialogBuilder
import com.ctrlz.beslim.model.User
import com.ctrlz.beslim.utils.ApiClient
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class SignUpFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val curtainView = inflater.inflate(R.layout.singup_layout, container, false)

        val regButton: Button = curtainView.findViewById(R.id.continueButton)

        regButton.setOnClickListener {
            lifecycleScope.launch {
                try {

                    val nameValue: String =
                        curtainView.findViewById<EditText>(R.id.nameField).text.toString()
                    val emailValue: String =
                        curtainView.findViewById<EditText>(R.id.emailField).text.toString()
                    val passwordValue: String =
                        curtainView.findViewById<EditText>(R.id.passwordField).text.toString()


                    val nameField =
                        curtainView.findViewById<TextInputLayout>(R.id.nameInputLayout)
                    val emailField =
                        curtainView.findViewById<TextInputLayout>(R.id.emailInputLayout)
                    val passwordField =
                        curtainView.findViewById<TextInputLayout>(R.id.passwordInputLayout)

                    val user = User(
                        firstName = nameValue,
                        lastName = "null",
                        email = emailValue,
                        password = passwordValue,
                        age = "null",
                        weight = "null",
                        height = "null"
                    )


                    if (nameValue.isNotEmpty() || emailValue.isNotEmpty() || passwordValue.isNotEmpty()) {

                        val response = ApiClient.api.addUser(user)

                        if (response.isSuccessful) {
                            DialogBuilder().infoDialog(
                                "Success",
                                response.body()?.message ?: "Хз че произошло ._.",
                                requireContext()
                            )
                            dismiss()
                        } else {
                            DialogBuilder().infoDialog(
                                "Error:",
                                response.errorBody()?.string() ?: "Хз че произошло ._.",
                                requireContext()
                            )
                            dismiss()
                        }
                    } else {
                        nameField.error = "null"
                        emailField.error = "null"
                        passwordField.error = "null"
                    }


                } catch (e: Exception) {
                    DialogBuilder().infoDialog(
                        "API Exception:",
                        e.message ?: "Хз че произошло ._.",
                        requireContext()
                    )
                }
            }
        }

        return curtainView
    }
}


