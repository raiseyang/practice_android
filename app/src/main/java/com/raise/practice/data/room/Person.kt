package com.raise.practice.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(
    val name: String,
    val age: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

