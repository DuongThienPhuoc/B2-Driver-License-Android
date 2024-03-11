package com.fpt.phuocdt.b2driverslicense.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserChoiceDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addChoice(userChoice: UserChoice)

    @Query("SELECT * FROM user_answer")
    suspend fun readAllData(): List<UserChoice>

    @Query("SELECT * FROM user_answer WHERE questionId = :id")
    suspend fun getUserChoiceById(id: String): UserChoice?

    @Query("DELETE FROM user_answer")
    suspend fun deleteAll()
}