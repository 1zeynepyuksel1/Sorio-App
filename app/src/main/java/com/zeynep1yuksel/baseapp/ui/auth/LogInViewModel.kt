package com.zeynep1yuksel.baseapp.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zeynep1yuksel.baseapp.data.SorioAuth
class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    fun onEmailChange(newEmail: String) {
        email = newEmail
    }
    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }
    fun login(authManager: SorioAuth, onSuccess: () -> Unit) {
        isLoading = true
        errorMessage = null

        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Lütfen tüm alanları doldurun."
            isLoading = false
            return
        }
        authManager.logIn(
            email = email,
            password = password,
            onSuccess = {
                isLoading = false
                onSuccess()
            },
            onFailure = {
                isLoading = false
            }
        )
    }
}

