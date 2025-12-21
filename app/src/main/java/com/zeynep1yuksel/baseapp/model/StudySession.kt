package com.zeynep1yuksel.baseapp.model

import kotlin.time.Duration

data class StudySession(
    val date:String="",
    val duration: Long=0,
    val timestamp:com.google.firebase.Timestamp?=null,
    val time: String=""
)
