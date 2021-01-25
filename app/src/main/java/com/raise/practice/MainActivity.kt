package com.raise.practice

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.raise.practice.adapter.ButtonAdapter
import com.raise.practice.databinding.ActivityMainBinding
import com.raise.weapon_base.LLog
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        val dataSet = arrayOf(
                "NullPointer",
                "/0",
                "主线程睡眠",
                "按钮4",
                "按钮5",
                "模拟后台异常"
        )
    }

    private fun clickBtn1() {
        printLog("clickBtn1() start")
        throw NullPointerException("clickBtn1() 模拟抛出异常NullPointerException")
    }

    private fun clickBtn2() {
        printLog("clickBtn2() start")
        val a = 2
        val b = a / 0
    }

    private fun clickBtn3() {
        printLog("clickBtn3() start,主线程睡眠")
        SystemClock.sleep(TimeUnit.MINUTES.toMillis(30))
    }

    private fun clickBtn4() {
        printLog("clickBtn4() start")
    }

    private fun clickBtn5() {
        printLog("clickBtn5() start")
    }

    private fun clickBtn6() {
        printLog("clickBtn6() start")
        printLog("广播：adb shell am broadcast -a com.raise.practice.exception --ei type 1" +
                "\n1. 空指针异常" +
                "\n2. 除0 异常" +
                "\n3. ANR"
        )
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvBtns.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            val btnAdapter = ButtonAdapter(dataSet).apply {
                mListener = object : ButtonAdapter.OnClickButton {
                    override fun onClick(index: Int) {
                        when (index + 1) {
                            1 -> clickBtn1()
                            2 -> clickBtn2()
                            3 -> clickBtn3()
                            4 -> clickBtn4()
                            5 -> clickBtn5()
                            6 -> clickBtn6()
                        }
                    }
                }
            }
            adapter = btnAdapter
        }

        registerReceiver(broadcastReceiver, IntentFilter("com.raise.practice.exception"))
    }

    override fun onDestroy() {
        unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    //adb shell am broadcast -a com.raise.practice.exception --ei type 1
    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            printLog("onReceive() intent.action=" + intent!!.action)
            val type = intent!!.getIntExtra("type", 0)
            printLog("onReceive() type=$type")
            when (type) {
                1 -> {
                    printLog("throw java.lang.NullPointerException")
                    Handler().postDelayed({ throw java.lang.NullPointerException("test null pointer ....") }, 1000)
                }
                2 -> {
                    printLog("a / 0   除0异常")
                    val a = 2
                    val b = a / 0
                }
                3 -> {
                    printLog("睡眠TimeUnit.MINUTES.toMillis(5)")
                    SystemClock.sleep(TimeUnit.MINUTES.toMillis(5))
                }
            }
            printLog("onReceive() end. intent.action=" + intent!!.action)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun printLog(msg: String) {
        LLog.i(TAG, "printLog() msg=$msg")
        binding.tvContent.text = "${binding.tvContent.text}\n$msg"
        runOnUiThread {
            binding.svContent.fullScroll(View.FOCUS_DOWN)
        }
    }

}
