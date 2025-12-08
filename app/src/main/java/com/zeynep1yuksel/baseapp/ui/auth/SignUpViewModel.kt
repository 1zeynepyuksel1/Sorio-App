package com.zeynep1yuksel.baseapp.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zeynep1yuksel.baseapp.data.SorioAuth

class SignUpViewModel : ViewModel() {
    var name by mutableStateOf("")
    var surname by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    fun onNameChange(newValue: String) { name = newValue }
    fun onSurnameChange(newValue: String) { surname = newValue }
    fun onEmailChange(newValue: String) { email = newValue }
    fun onPasswordChange(newValue: String) { password = newValue }
    fun onConfirmPasswordChange(newValue: String) { confirmPassword = newValue }
    fun signUp(authManager: SorioAuth, onSuccess: () -> Unit) {
        isLoading = true
        errorMessage = null
        if (password != confirmPassword) {
            errorMessage = "Şifreler uyuşmuyor!"
            isLoading = false
            return
        }
        if (name.isBlank() || surname.isBlank() || email.isBlank() || password.isBlank()) {
            errorMessage = "Lütfen tüm alanları doldurun."
            isLoading = false
            return
        }
        authManager.signUp(
            name = name,
            surname = surname,
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

