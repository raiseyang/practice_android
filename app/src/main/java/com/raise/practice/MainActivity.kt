package com.raise.practice

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.raise.practice.adapter.ButtonAdapter
import com.raise.practice.data.PersonRoomDataSource
import com.raise.practice.databinding.ActivityMainBinding
import com.raise.practice.dialog.IDialog
import com.raise.weapon_base.LLog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// @AndroidEntryPoint 标记一个Hilt组件；  要在该类下使用Hilt必须标记
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // 注入一个实例
    @Inject
    lateinit var deviceUtil: DeviceUtil
    // 注入一个接口实现
    @Inject
    lateinit var loadingDialog: IDialog
    // 注入一个有依赖项的实例
    @Inject
    lateinit var personDS: PersonRoomDataSource

    companion object {
        const val TAG = "MainActivity"
        val dataSet = arrayOf(
            "按钮1",
            "按钮2",
            "按钮3",
            "按钮4",
            "按钮5",
            "申请读取T卡权限"
        )
    }

    private fun clickBtn1() {
        printLog("clickBtn1() start")
        printLog("deviceUtil.sdk=${deviceUtil.sdk}")
    }

    private fun clickBtn2() {
        printLog("clickBtn2() start")
        loadingDialog.showLoading()
    }

    private fun clickBtn3() {
        printLog("clickBtn3() start")
        printLog("personDS=${personDS.getAllPerson().size}")
    }

    private fun clickBtn4() {
        printLog("clickBtn4() start")

    }

    private fun clickBtn5() {
        printLog("clickBtn5() start")

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun clickBtn6() {
//        ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

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
