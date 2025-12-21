package com.zeynep1yuksel.baseapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.zeynep1yuksel.baseapp.data.SorioRepository
import com.zeynep1yuksel.baseapp.model.SorioUser

class HomeViewModel: ViewModel() {
    val repository= SorioRepository()
    val auth= FirebaseAuth.getInstance()
    var currentUser by mutableStateOf<SorioUser?>(null)
    var isLoading by mutableStateOf(false)

    init {
        fetchCurrentUserProfile()
    }
    private fun fetchCurrentUserProfile(){
        val currentUserUid=auth.currentUser?.uid
        if(currentUserUid!=null){
            isLoading=true
            repository.getUser(
                uid=currentUserUid,
                onSuccess = {user->
                    currentUser=user
                    isLoading=false
                },
                onFailure = {errorMsg->
                    println("kullanıcı verisi çekilirken hata:$errorMsg")
                    isLoading=false
                }
            )
        }
    }
}