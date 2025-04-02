package com.ctrlz.beslim.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctrlz.beslim.api.ApiClient
import com.ctrlz.beslim.model.PostRegister
import com.ctrlz.beslim.utils.DialogBuilder
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _registrationResult = MutableLiveData<RegistrationResult>()
    val registrationResult: LiveData<RegistrationResult> get() = _registrationResult

    fun registerUser(
        nameValue: String,
        emailValue: String,
        passwordValue: String,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                val postRegister = PostRegister(
                    fullName = nameValue,
                    email = emailValue,
                    password = passwordValue,
                )
                if (validateFields(nameValue, emailValue, passwordValue)) {
                    performRegistration(postRegister, context)
                } else{
                   _registrationResult.value = RegistrationResult.ValidationError("Fill all fields")
                }
            } catch (e: Exception) {
                _registrationResult.value = RegistrationResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun validateFields(
        nameValue: String,
        emailValue: String,
        passwordValue: String
    ): Boolean {
        return nameValue.isNotEmpty() && emailValue.isNotEmpty() && passwordValue.isNotEmpty()
    }

    private suspend fun performRegistration(postRegister: PostRegister, context: Context) {
        val response = ApiClient.registrationApi.registerUser(postRegister)

        if (response.isSuccessful) {
            _registrationResult.value = RegistrationResult.Success(response.body()?.message ?: "Unknown message")
        } else {
            _registrationResult.value =
                RegistrationResult.Error(response.errorBody()?.string() ?: "Unknown error")
        }
    }

    sealed class RegistrationResult {
        data class Success(val message: String) : RegistrationResult()
        data class Error(val errorMessage: String) : RegistrationResult()
        data class ValidationError(val message : String) : RegistrationResult()
    }

    fun showDialog(result: RegistrationResult, context: Context) {
        when (result) {
            is RegistrationResult.Success -> DialogBuilder().infoDialog("Success", result.message, context)
            is RegistrationResult.Error -> DialogBuilder().infoDialog("Error:", result.errorMessage, context)
            is RegistrationResult.ValidationError -> DialogBuilder().infoDialog(
                "Validation Error:",
                result.message,
                context)
        }
    }
}