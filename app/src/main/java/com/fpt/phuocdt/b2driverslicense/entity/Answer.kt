package com.fpt.phuocdt.b2driverslicense.entity

import java.io.Serializable

data class Answer(
    val _id: String,
    val isCorrect: Boolean,
    val text: String
): Serializable