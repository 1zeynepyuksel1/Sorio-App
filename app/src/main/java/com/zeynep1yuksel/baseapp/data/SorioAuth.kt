package com.zeynep1yuksel.baseapp.data

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
class SorioAuth(private val context: Context) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    fun signUp(email: String, password: String, onSuccess: () -> Unit) {

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Geçersiz email formatı!", Toast.LENGTH_SHORT).show()
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { gorev ->
                if (gorev.isSuccessful) {
                    val user = auth.currentUser

                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { mailGorevi ->
                            if (mailGorevi.isSuccessful) {
                                Toast.makeText(context, "Doğrulama maili gönderildi! Lütfen kutunu kontrol et.", Toast.LENGTH_LONG).show()
                                onSuccess()
                            }
                        }
                } else {
                    val hata = gorev.exception?.localizedMessage ?: "Hata oluştu."
                    Toast.makeText(context, hata, Toast.LENGTH_LONG).show()
                }
            }
    }
    fun signIn(email: String, password: String, onSuccess: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { gorev ->
                if (gorev.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        Toast.makeText(context, "Hoş geldin!", Toast.LENGTH_SHORT).show()
                        onSuccess()
                    } else {
                        Toast.makeText(context, "Lütfen önce mail adresini doğrula!", Toast.LENGTH_LONG).show()
                        auth.signOut()
                    }
                } else {
                    Toast.makeText(context, "Giriş Başarısız: Şifre veya Email yanlış.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}