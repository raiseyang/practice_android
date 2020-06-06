package com.raise.practice

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.abupdate.common.Trace
import com.abupdate.common_kt.postUI
import com.raise.practice.data.User
import com.raise.practice.data.userDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 在ViewModel中使用协程
 * 协程会在viewmodel销毁时，自动cancel掉job（viewModelScope范围内）
 * Created by raise.yang on 20/06/05.
 */
class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    /**
     * LiveData的协程扩展【liveData】
     * 只有当user被观察时，协程才开始执行；注意和`MutableLiveData().apply{}`的区别
     */
    val user: LiveData<User> = liveData {
        Trace.d(TAG, "() 开始发送")
        userDao().queryAll().forEach {
            emit(it)
        }
    }


    /**
     * viewmodel的协程扩展【viewModelScope】
     */
    fun checkVersion() {
        val job = viewModelScope.launch {
            // 调用协程函数，此时协程在主线程运行
            Trace.d(TAG, "checkVersion() in [viewModelScope.launch]")
            withContext(Dispatchers.Default) {
                // 切换到子线程执行协程
                delay(1000)
                Trace.d(TAG, "checkVersion() in [withContext(Dispatchers.Default)]")
            }
            delay(1000)
            Trace.d(TAG, "checkVersion() in [viewModelScope.launch] after")

        }
        postUI(1500) {
            Trace.d(TAG, "checkVersion() job.cancel()")
            job.cancel()
        }
    }


}