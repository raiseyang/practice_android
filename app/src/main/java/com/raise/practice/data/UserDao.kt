package com.raise.practice.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by raise.yang on 20/06/05.
 */
@Dao
interface UserDao {
    /**
     * 增加了suspend,编程协程函数
     * 可以在主线程调用插入方法了
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg user: User)

    /**
     * 注意：LiveData不能和协程一起使用
     */
    @Query("SELECT * FROM user ")
    suspend fun queryAll(): List<User>
}