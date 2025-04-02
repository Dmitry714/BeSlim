package com.ctrlz.beslim.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctrlz.beslim.api.ApiClient
import com.ctrlz.beslim.model.PostLogin
import com.ctrlz.beslim.utils.DialogBuilder
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> get() = _loginResult

    fun loginUser(
        emailValue: String,
        passwordValue: String,
        context: Context
    ){
        viewModelScope.launch {
            try{
                val postLogin = PostLogin(
                    email = emailValue,
                    password = passwordValue
                )

                if (validateFields(emailValue, passwordValue)){
                    performLogin(postLogin, context)
                } else{
                    _loginResult.value = LoginResult.Error("Fill all fields")
                }

            }
            catch (e: Exception){
                _loginResult.value = LoginResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun validateFields(
        emailValue: String,
        passwordValue: String
    ): Boolean{
        return emailValue.isNotEmpty() && passwordValue.isNotEmpty()
    }

    private suspend fun performLogin(postLogin: PostLogin, context: Context){
        val response = ApiClient.authenticationApi.loginUser(postLogin)

        if (response.isSuccessful){
            _loginResult.value = LoginResult.Success(response.body()?.message ?: "Unknown message")
        } else {
            _loginResult.value = LoginResult.Error(response.errorBody()?.string() ?: "Unknown error")
        }
    }

    sealed class LoginResult{
        data class Success(val message: String) : LoginResult()
        data class Error(val errorMessage: String) : LoginResult()
    }

    fun showDialog(result: LoginResult, context: Context){
        when(result){
            is LoginResult.Success -> DialogBuilder().infoDialog("Success", result.message, context)
            is LoginResult.Error -> DialogBuilder().infoDialog("Error:", result.errorMessage, context)
        }
    }




}