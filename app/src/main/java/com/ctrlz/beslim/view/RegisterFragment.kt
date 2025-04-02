package com.ctrlz.beslim.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.ctrlz.beslim.R
import com.ctrlz.beslim.viewModel.RegisterViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout


class RegisterFragment : BottomSheetDialogFragment() {

    private val viewModel : RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val curtainView = inflater.inflate(R.layout.singup_layout, container, false)

        val nameField = curtainView.findViewById<EditText>(R.id.nameField)
        val emailField = curtainView.findViewById<EditText>(R.id.emailField)
        val passwordField = curtainView.findViewById<EditText>(R.id.passwordField)

        val nameInputLayout = curtainView.findViewById<TextInputLayout>(R.id.nameInputLayout)
        val emailInputLayout = curtainView.findViewById<TextInputLayout>(R.id.emailInputLayout)
        val passwordInputLayout = curtainView.findViewById<TextInputLayout>(R.id.passwordInputLayout)
        val regButton: Button = curtainView.findViewById(R.id.continueButton)

        viewModel.registrationResult.observe(viewLifecycleOwner) { result ->
            viewModel.showDialog(result, requireContext())
        }

        regButton.setOnClickListener {
            val nameValue = nameField.text.toString()
            val emailValue = emailField.text.toString()
            val passwordValue = passwordField.text.toString()

            viewModel.registerUser(nameValue, emailValue, passwordValue, requireContext())
        }
        return curtainView
    }
}


