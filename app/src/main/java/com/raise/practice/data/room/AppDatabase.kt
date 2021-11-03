package com.raise.practice.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Person::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao

}