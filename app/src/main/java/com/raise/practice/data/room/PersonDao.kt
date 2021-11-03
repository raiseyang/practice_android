package com.raise.practice.data.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PersonDao {

    @Query("SELECT * FROM Person ORDER BY id DESC")
    fun getAll(): List<Person>

}