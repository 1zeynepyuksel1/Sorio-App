package com.zeynep1yuksel.baseapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeynep1yuksel.baseapp.data.SorioRepository
import com.zeynep1yuksel.baseapp.model.StudySession
import com.zeynep1yuksel.baseapp.model.TimerState
import com.zeynep1yuksel.baseapp.model.TimerStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {
    private val repository =  SorioRepository()
    private val _sessions = MutableStateFlow<List<StudySession>>(emptyList())
    val sessions: StateFlow<List<StudySession>> = _sessions
    private val _timerState = MutableStateFlow(TimerState())
    val timerState: StateFlow<TimerState> = _timerState
    private var timerJob: Job? = null
    var totalSavedTimeSeconds by mutableStateOf(0L)
        private set
    private val motivations = listOf(
        "Harika gidiyorsun, odaklanmaya devam et! 🚀",
        "Küçük adımlar, büyük başarılar getirir. ✨",
        "Zihnin bir kas gibidir, şu an onu güçlendiriyorsun. 💪",
        "Başarı, her gün tekrarlanan küçük çabaların toplamıdır.",
        "Derin bir nefes al ve devam et, Sorio seninle! ⚡"
    )

    private val _currentMotivation = MutableStateFlow(motivations.random())
    val currentMotivation: StateFlow<String> = _currentMotivation
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
    private fun startTimer() {
        if (timerJob?.isActive == true) return
        timerJob = viewModelScope.launch {
            _timerState.update { it.copy(status = TimerStatus.RUNNING) }

            while (timerState.value.status == TimerStatus.RUNNING) {
                delay(1000L)
                _timerState.update { currentState ->
                    currentState.copy(studyTimeSeconds = currentState.studyTimeSeconds + 1)
                }
                if (timerState.value.studyTimeSeconds % 30 == 0L) {
                    _currentMotivation.value = motivations.random()
                }
            }
        }
    }

    fun toggleTimer() {
        val currentStatus = timerState.value.status
        when (currentStatus) {
            TimerStatus.RUNNING -> {
                timerJob?.cancel()
                _timerState.update { it.copy(status = TimerStatus.PAUSED) }
            }
            TimerStatus.PAUSED, TimerStatus.RESET -> {
                startTimer()
            }
        }
    }
    fun resetTimer() {
        timerJob?.cancel()
        timerJob = null
        _timerState.update {
            it.copy(
                studyTimeSeconds = 0,
                status = TimerStatus.RESET
            )
        }
    }
    fun saveTime(){
        val timeToSave= _timerState.value.studyTimeSeconds
        if (timeToSave > 0) {
            viewModelScope.launch {
                val result = repository.saveStudySession(timeToSave)
                result.onSuccess {
                    resetTimer()
                    fetchUserSessions()
                }.onFailure { e ->
                    println("Kayıt hatası: ${e.message}")
                }
            }
        }
    }
}