package com.raise.practice

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.abupdate.common.Trace
import com.abupdate.common_kt.postUI
import com.raise.practice.data.User
import com.raise.practice.data.userDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * lifecycle的协程扩展【lifecycleScope】
     * 协程范围内代码是顺序执行
     * 顺序执行时，可以使用withContext()切换代码执行的线程
     */
    fun test1(view: View) {

        lifecycleScope.launch {
            Trace.d(TAG, "test1() 1")
            val sizeOnByte = 1000000000000000
            val tv_title = withContext(Dispatchers.Default) {
                TextView(this@MainActivity).apply {
                    // 模拟耗时的计算
                    text = (sizeOnByte / 1024).toString()
                    Trace.d(TAG, "test1() 2")
                }
            }
            Trace.d(TAG, "test1() 3")
            (window.decorView.rootView as ViewGroup).addView(tv_title)
        }
    }

    /**
     * lifecycleScope协程范围内默认是主线程
     */
    fun test2(view: View) {
        Trace.d(TAG, "test2() ")
        thread {
            Trace.d(TAG, "test2() 另起一个子线程：${Thread.currentThread().name}")
            lifecycleScope.launch {
                Trace.d(TAG, "test2() default:${Thread.currentThread().name}")
                Toast.makeText(this@MainActivity, "主线程吐司", Toast.LENGTH_SHORT).show()
            }
        }

    }

    /**
     * 在主线程非阻塞式挂起
     */
    fun test3(view: View) {
        Trace.d(TAG, "test3() ")
        lifecycleScope.launch {
            // 挂起15s
            repeat(15) {
                Trace.i(TAG, "test3() count=$it")
                delay(1000)
            }
            Toast.makeText(this@MainActivity, "主线程吐司", Toast.LENGTH_SHORT).show()
        }
        postUI(5000) {
            Toast.makeText(this@MainActivity, "延迟5s弹出", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 阻塞线程
     */
    fun test4(view: View) {
        Trace.d(TAG, "test4() ")
        lifecycleScope.launch {
            // 挂起15s
            repeat(10) {
                Trace.i(TAG, "test4() count=$it")
                SystemClock.sleep(1000)
            }
            Toast.makeText(this@MainActivity, "主线程吐司", Toast.LENGTH_SHORT).show()
        }
        postUI(5000) {
            Toast.makeText(this@MainActivity, "延迟5s弹出", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 非协程范围内，调用协程函数，需要添加suspend关键字标识；
     * 代表这是一个协程函数
     */
    private suspend fun showToastDelay(time: Long) {
        delay(time)
        Toast.makeText(this@MainActivity, "延迟${time}弹出", Toast.LENGTH_SHORT).show()
    }

    fun test5(view: View) {
//        showToastDelay(1000) //协程函数，只能在协程范围内调用
    }

    /**
     * 验证room的线程安全
     */
    fun test6(view: View) {
        Trace.d(TAG, "test6() ")
        lifecycleScope.launch {
            Trace.d(TAG, "test6() 添加10条数据")
            repeat(10) {
                userDao().insert(User("杨东升$it 号"))
            }
            Trace.d(TAG, "test6() 监听User表")
            showToastDelay(100)
            //在主线程查询数据
            userDao().queryAll().forEach {
                Trace.i(TAG, "test6() $it")
            }
            Trace.d(TAG, "test6() 第二次添加5条数据")
            repeat(5) {
                userDao().insert(User("小样 $it"))
            }
        }
    }

    /**
     * 验证livedata协程扩展
     */
    fun test7(view: View) {
        viewModel.user.observe(this, Observer {
            Trace.d(TAG, "test7() ${it.name}")
            Toast.makeText(this@MainActivity, "liveData:User:${it.name}", Toast.LENGTH_SHORT).show()
        })
    }

    fun test8(view: View) {}

}
