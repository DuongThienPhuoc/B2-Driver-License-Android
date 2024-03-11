package com.fpt.phuocdt.b2driverslicense.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_answer")
data class UserChoice(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val questionId: String,
    val answerId: String
)