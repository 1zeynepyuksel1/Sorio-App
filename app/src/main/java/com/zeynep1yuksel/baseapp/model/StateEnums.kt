package com.zeynep1yuksel.baseapp.model

enum class QuestionState(val value:String){
    PENDING("pending"),
    MATCHED("matched"),
    CLOSED("closed")
}
enum class AnswerState(val value:String){
    WAITING("waiting"),
    REVIEWING("reviewing"),
    ACCEPTED("accepted"),
    HELPFUL("helpful"),
    REJECTED("rejected")
}

