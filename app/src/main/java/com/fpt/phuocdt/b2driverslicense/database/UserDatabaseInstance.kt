package com.fpt.phuocdt.b2driverslicense.database

import android.content.Context
import androidx.room.Room

object UserDatabaseInstance {
    @Volatile
    private var INSTANCE: UserDatabase? = null
    fun getInstance(context: Context): UserDatabase {
        if (INSTANCE == null) {
            synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
            }
        }
        return INSTANCE!!
    }
}