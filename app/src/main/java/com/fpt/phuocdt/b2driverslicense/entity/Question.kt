package com.fpt.phuocdt.b2driverslicense.entity

data class Question(
    val _id: String,
    val answers: List<Answer>,
    val img: String,
    val question: String
)