package com.raise.practice

import android.annotation.SuppressLint
import android.os.Bundle
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
                "按钮6"
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
