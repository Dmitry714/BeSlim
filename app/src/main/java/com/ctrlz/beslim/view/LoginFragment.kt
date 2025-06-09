package com.ctrlz.beslim.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import com.ctrlz.beslim.R
import com.ctrlz.beslim.api.ApiClient
import com.ctrlz.beslim.model.PostLogin
import com.ctrlz.beslim.utils.DialogBuilder
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class LoginFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.signin_layout, container, false)

        val singInLink: Button = view.findViewById(R.id.continueButton)

        val emailField: TextInputEditText = view.findViewById(R.id.emailField)
        val passwordField: TextInputEditText = view.findViewById(R.id.passwordField)
        val emailInputLayout: TextInputLayout = view.findViewById(R.id.emailInputLayout)
        val passwordInputLayout: TextInputLayout = view.findViewById(R.id.passwordInputLayout)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        val continueButton: Button = view.findViewById(R.id.continueButton)

        val intent = Intent(requireContext(), HomeActivity::class.java)


        singInLink.setOnClickListener {

            lifecycleScope.launch {

                try {

                    progressBar.visibility = View.VISIBLE
                    continueButton.visibility = View.GONE

                    val emailValue = emailField.text.toString()
                    val passwordValue = passwordField.text.toString()

                    val postLogin = PostLogin(
                        email = emailValue,
                        password = passwordValue
                    )

                    if (emailValue.isNotEmpty() && passwordValue.isNotEmpty()) {

                        val response = ApiClient.authenticationApi.loginUser(postLogin)

                        if (response.isSuccessful) {

                            if (response.body()?.message == "Login successful") {
                                startActivity(intent)
                                dismiss()

                            }
                            else{
                                DialogBuilder().infoDialog(
                                    "Error",
                                    response.body()?.message ?: "An error occurred while trying to log in",
                                    requireContext()
                                )
                            }
                        } else {
                            DialogBuilder().infoDialog(
                                "Error:",
                                response.errorBody()?.string() ?: "Error sending request to server",
                                requireContext()
                            )
                            dismiss()
                        }
                    } else {
                        emailInputLayout.error =
                            if (emailValue.isEmpty()) "Fill the field" else null
                        passwordInputLayout.error =
                            if (passwordValue.isEmpty()) "Fill the field" else null
                    }

                } catch (e: Exception) {
                    DialogBuilder().infoDialog(
                        "API Exception:",
                        e.message ?: "Unknown error",
                        requireContext()
                    )
                } finally {
                    continueButton.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
        }
        return view
    }
}
