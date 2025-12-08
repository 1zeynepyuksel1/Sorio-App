package com.zeynep1yuksel.baseapp.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Answer(
    val answerId: String = "",
    val questionId: String = "",
    val ownerUid: String = "",
    val textContent: String = "",
    val imageUrl: String? = null,
    val state: AnswerState = AnswerState.WAITING,
    val solutionScore: Int = 0,
    @ServerTimestamp
    val createdAt: Date? = null
)