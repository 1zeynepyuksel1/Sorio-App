package com.zeynep1yuksel.baseapp.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Question(
    val questionId: String = "",
    val ownerUid: String = "",
    val imageUrl: String = "",
    val textContent: String? = null,
    val ocrResult: String = "",
    val subject: String = "Genel",
    val topic: String = "Belirsiz",
    val acceptedAnswerId: String? = null,
    val state: QuestionState = QuestionState.PENDING,
    @ServerTimestamp
    val createdAt: Date? = null,
    val answerCount: Int = 0,
    val matchCloseTime: Long = 0
)