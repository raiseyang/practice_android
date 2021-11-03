package com.raise.practice.data

import com.raise.practice.data.room.Person
import com.raise.practice.data.room.PersonDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Inject 提供的绑定依赖PersonDao
 */
@Singleton
class PersonRoomDataSource @Inject constructor(private val personDao: PersonDao) {
    fun getAllPerson(): List<Person> {
//        return personDao.getAll()
        // mock
        return listOf(
            Person("Raise",18),
            Person("Eric",18),
            Person("Robin",18),
        )
    }
}