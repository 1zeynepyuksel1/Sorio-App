package com.zeynep1yuksel.baseapp.data

import com.google.firebase.firestore.FirebaseFirestore
import com.zeynep1yuksel.baseapp.model.SorioUser

class SorioRepository {
    val db= FirebaseFirestore.getInstance()
    fun saveUser(
        user: SorioUser,
        onSuccess:()->Unit,
        onFailure:(String)->Unit
    ){
        db.collection("users").document(user.uid).set(user)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {e->
                onFailure(e.localizedMessage ?: "veritabanına yazma hatası")
            }
    }
    fun getUser(
        uid:String,
        onSuccess: (SorioUser?) -> Unit,
        onFailure: (String) -> Unit
    ){
        db.collection("users").document(uid).get()
            .addOnSuccessListener {document->
                if(document!=null && document.exists()){
                    val sorioUser=document.toObject(SorioUser::class.java)
                    onSuccess(sorioUser)
                }else{
                    onSuccess(null)
                }
            }
            .addOnFailureListener { e->
                onFailure(e.localizedMessage ?: "Veri Çekilemedi")
            }

    }
}