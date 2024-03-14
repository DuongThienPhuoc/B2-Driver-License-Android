package com.fpt.phuocdt.b2driverslicense.entity

import java.io.Serializable

data class Question (
    val _id: String,
    val answers: List<Answer>,
    val img: String?,
    val question: String,
    val topic: Number
) : Serializable