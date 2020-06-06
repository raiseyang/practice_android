package com.raise.practice.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by raise.yang on 20/06/05.
 */
@Entity
data class User(@PrimaryKey var name: String) {

    var age: Int = 0

}