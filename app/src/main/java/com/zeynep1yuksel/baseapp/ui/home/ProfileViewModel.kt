package com.zeynep1yuksel.baseapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.zeynep1yuksel.baseapp.data.SorioRepository
import com.zeynep1yuksel.baseapp.model.StudySession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val repository = SorioRepository()

    private val _sessions = MutableStateFlow<List<StudySession>>(emptyList())
    val sessions: StateFlow<List<StudySession>> = _sessions
    private val _userGoal = MutableStateFlow(4)
    val userGoal: StateFlow<Int> = _userGoal
    val totalFocusTime = sessions.map { it.sumOf { s -> s.duration } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0L)

    val sessionCount = sessions.map { it.size }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    init {
        fetchUserSessions()
    }

    fun fetchUserSessions() {
        viewModelScope.launch {
            val result = repository.getUserSessions()

            result.onSuccess {sessionList->
                _sessions.value = sessionList
            }.onFailure { exception ->
                println("veri çekme hatası : ${exception.message}")
            }
        }
    }
    fun updateDailyGoal(newGoal:Int){
        _userGoal.value=newGoal
    }

}