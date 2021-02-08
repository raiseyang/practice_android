package com.raise.practice

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.eclipsesource.v8.*
import com.raise.autorunner.WAccessibilityService
import com.raise.jsengine.Console
import com.raise.jsengine.GlobalFunc
import com.raise.practice.adapter.ButtonAdapter
import com.raise.practice.databinding.ActivityMainBinding
import com.raise.weapon_base.LLog
import com.raise.weapon_ui.ToastUtil
import com.raise.weapon_ui.floatwindow.FloatWindow
import com.raise.weapon_ui.floatwindow.IFloatWindow
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        val dataSet = arrayOf(
                "弹出悬浮窗",
                "跳到辅助设置",
                "1",
                "1",
                "1",
                "1"
        )
    }

    private lateinit var binding: ActivityMainBinding

    private var floatWindow: IFloatWindow? = null

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

    private fun clickBtn1() {
        printLog("clickBtn1() start")

        val button1 = Button(this)
        button1.setOnClickListener {
            ToastUtil.show("点击今日头条")
            thread {
                val v8 = V8.createV8Runtime()
                Console.injectV8(v8)
                GlobalFunc.injectV8(v8)
                v8.executeScript("""


click("今日头条");
sleep(5000);
click("上海");
for (i = 0; i < 10; i++) {
    console.info("第"+i+"次上划")
    scrollUp();
//    if( i == 3){
        
//    sleep(1000)
//    scrollDown();
//    }

    sleep(5000)
}




        """.trimIndent())
            }
        }

        floatWindow = FloatWindow.Builder(WAccessibilityService.instance ?: this)
                .setView(button1)
                .create()

        floatWindow?.show()
    }

    private fun clickBtn2() {
        printLog("clickBtn2() start")
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent)
    }

    private fun clickBtn3() {
        printLog("clickBtn3() start")
        floatWindow?.hide()
    }

    private fun clickBtn4() {
        printLog("clickBtn4() start")
    }

    private fun clickBtn5() {
        printLog("clickBtn5() start")
        registerConsoleApi2()
    }

    private fun clickBtn6() {
        printLog("clickBtn6() start")
    }


    @SuppressLint("SetTextI18n")
    private fun printLog(msg: String) {
        LLog.i(TAG, "printLog() msg=$msg")
        binding.tvContent.text = "${binding.tvContent.text}\n$msg"
        runOnUiThread {
            binding.svContent.fullScroll(View.FOCUS_DOWN)
        }
    }

    // 自己写的api
    private fun registerConsoleApi2() {
        val v8 = V8.createV8Runtime()
        com.raise.jsengine.Console.injectV8(v8)
        GlobalFunc.injectV8(v8)
        v8.executeScript("""
            console.log('hello, world');
            toast("hello, toast content");
            sleep(2000);
            console.log('hello, world  2s');
        """.trimIndent())
    }

}
