package com.raise.practice

import android.annotation.SuppressLint
import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.raise.practice.adapter.ButtonAdapter
import com.raise.practice.databinding.ActivityMainBinding
import com.raise.weapon_base.LLog
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        val dataSet = arrayOf(
                "跳转到辅助设置",
                "按钮2",
                "按钮3",
                "按钮4",
                "按钮5",
                "按钮6"
        )
    }

    private lateinit var binding: ActivityMainBinding
//    private lateinit var mDevice: UiDevice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize UiDevice instance
        val instrumentation = getInstrumentation()
        LLog.d(TAG, "onCreate() instrumentation=$instrumentation")
//        mDevice = UiDevice.getInstance(instrumentation)

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
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent)
    }

    private fun clickBtn2() {
        printLog("clickBtn2() start")
        thread {
            printLog("clickBtn2() start in other thread")
            Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_UP)
        }
        printLog("clickBtn2() end.")
    }

    private fun clickBtn3() {
        thread {
            printLog("clickBtn2() start in other thread")
            Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_DOWN)
        }
        printLog("clickBtn3() start")
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


    fun startMainActivityFromHomeScreen() {
//
//        // Start from the home screen
//        mDevice.pressHome()
//
//        // Wait for launcher
//        val launcherPackage: String = getLauncherPackageName()
//        assertThat(launcherPackage, notNullValue())
//        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT)
//
//        // Launch the blueprint app
//        val context: Context = applicationContext
//        val intent: Intent = context.getPackageManager()
//                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) // Clear out any previous instances
//        context.startActivity(intent)
//
//        // Wait for the app to appear
//        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT)
    }

    @SuppressLint("SetTextI18n")
    private fun printLog(msg: String) {
        LLog.i(TAG, "printLog() msg=$msg")
        runOnUiThread {
            binding.tvContent.text = "${binding.tvContent.text}\n$msg"
            binding.svContent.fullScroll(View.FOCUS_DOWN)
        }
    }

}
