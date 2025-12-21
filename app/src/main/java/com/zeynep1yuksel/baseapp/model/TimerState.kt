package com.zeynep1yuksel.baseapp.model

enum class TimerStatus{
    RUNNING,
    PAUSED,
    RESET
}
data class TimerState(
    val studyTimeSeconds:Long=0,
    val status: TimerStatus= TimerStatus.RESET
)