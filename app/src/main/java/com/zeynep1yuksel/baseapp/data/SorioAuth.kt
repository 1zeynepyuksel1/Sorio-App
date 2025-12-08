package com.zeynep1yuksel.baseapp.data

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.zeynep1yuksel.baseapp.model.SorioUser

class SorioAuth(private val context: Context) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val repository = SorioRepository()
    fun signUp(
        name: String,
        surname: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    val uid = firebaseUser?.uid

                    if (uid != null) {
                        val newUser = SorioUser(
                            uid = uid,
                            name = name,
                            surname = surname,
                            email = email
                        )
                        repository.saveUser(
                            user = newUser,
                            onSuccess = {
                                onSuccess()
                            },
                            onFailure = { errorMsg ->
                                Toast.makeText(context, "Veritabanı Hatası: $errorMsg", Toast.LENGTH_LONG).show()
                                onFailure()
                            }
                        )
                    }
                } else {
                    val error = task.exception?.localizedMessage ?: "Kayıt başarısız."
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    onFailure()
                }
            }
    }
    fun logIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    Toast.makeText(context, "Giriş Başarısız. Kontrol edin.", Toast.LENGTH_SHORT).show()
                    onFailure()
                }
            }
    }
    fun signOut() {
        auth.signOut()
    }
}