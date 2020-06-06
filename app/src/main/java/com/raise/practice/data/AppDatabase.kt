package com.raise.practice.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by raise.yang on 20/06/05.
 */
@Database(
        entities = arrayOf(
                User::class
        ), version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        lateinit var db: AppDatabase
        fun init(context: Context) {
            db = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "database-name"
            )
//                    .allowMainThreadQueries()
                    .build()
        }
    }
}

fun userDao() = AppDatabase.db.userDao()