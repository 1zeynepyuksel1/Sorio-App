package com.zeynep1yuksel.baseapp.data

import androidx.compose.animation.core.snap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.zeynep1yuksel.baseapp.model.Question
import com.zeynep1yuksel.baseapp.model.SorioUser
import com.zeynep1yuksel.baseapp.model.StudySession
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SorioRepository() {
    val auth= FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    fun saveUser(
        user: SorioUser,
        onSuccess:()->Unit,
        onFailure:(String)->Unit
    ){
        firestore.collection("users").document(user.uid).set(user)
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
        firestore.collection("users").document(uid).get()
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
    suspend fun saveStudySession(seconds: Long):Result<Unit> {
        return try {
            val auth=com.google.firebase.auth.FirebaseAuth.getInstance()
            val firestore=com.google.firebase.firestore.FirebaseFirestore.getInstance()

            val userId=auth.currentUser?.uid ?: return Result.failure(Exception("giriş yapılmadı."))

            val today = java.text.SimpleDateFormat("yyyy-MM-dd",java.util.Locale.getDefault()).format(java.util.Date())
            val calendar = java.util.Calendar.getInstance()
            val timeFormat = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
            val currentTime = timeFormat.format(calendar.time)
            val sessionData=hashMapOf(
                "date" to today,
                "duration" to seconds,
                "time" to currentTime,
                "timestamp" to com.google.firebase.firestore.FieldValue.serverTimestamp()
            )

            firestore.collection("users")
                .document(userId)
                .collection("sessions")
                .add(sessionData)
                .await()
            Result.success(Unit)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
    suspend fun uploadQuestion(question: Question): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Giriş yapılmadı"))
            val finalQuestion = question.copy(ownerUid = userId)

            firestore.collection("questions")
                .add(finalQuestion)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun getUserSessions():Result<List<StudySession>>{
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Giriş yok"))
             val snapshot = firestore.collection("users")
                 .document(userId)
                 .collection("sessions")
                 .orderBy("timestamp",com.google.firebase.firestore.Query.Direction.DESCENDING)
                 .get()
                 .await()
            val sessions= snapshot.toObjects(StudySession::class.java)
            Result.success(sessions)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun updateDailyGoal(newGoalHours: Int): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Giriş yok"))
            firestore.collection("users").document(userId)
                .update("dailyGoalHours", newGoalHours)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}