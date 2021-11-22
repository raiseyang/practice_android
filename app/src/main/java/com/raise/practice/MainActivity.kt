package com.raise.practice

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.*
import com.raise.practice.adapter.ButtonAdapter
import com.raise.practice.databinding.ActivityMainBinding
import com.raise.practice.work.UploadCoroutineWorker
import com.raise.practice.work.UploadWorker
import com.raise.weapon_base.LLog

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        val dataSet = arrayOf(
            "执行一个一次行最简单的工作",
            "按钮2",
            "按钮3",
            "按钮4",
            "按钮5",
            "申请读取T卡权限"
        )
    }

    private fun clickBtn1() {
        printLog("clickBtn1() start")
        val uploadWorkerRequest = OneTimeWorkRequest.from(UploadWorker::class.java)

//        val uploadWorkerRequest = OneTimeWorkRequestBuilder<UploadWorker>()
//            .build()
        WorkManager.getInstance(this).enqueue(uploadWorkerRequest)
    }

    private fun clickBtn2() {
        printLog("clickBtn2() start")
        //定义worker执行的约束条件：网络，充电状态等。
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED) // 需要有网络
            .build()

        val list = listOf<String>("1", "3")

        val uploadWorkerRequest = OneTimeWorkRequestBuilder<UploadWorker>().apply {
            setConstraints(constraints)
            // 给worker增加标记；WorkManager.cancelAllWorkByTag(String)可以取消worker
            addTag("upload_worker")
                .setInputData(
                    workDataOf(
                        "list" to list
                    )
                )
        }.build()
        WorkManager.getInstance(this).enqueue(uploadWorkerRequest)
    }

    private fun clickBtn3() {
        printLog("clickBtn3() start")

        val uploadWorkerRequest = OneTimeWorkRequestBuilder<UploadCoroutineWorker>().apply {
            addTag("upload_worker")
        }.build()


        WorkManager.getInstance(this).enqueue(uploadWorkerRequest)

        val workInfoLiveData = WorkManager.getInstance(applicationContext)
            .getWorkInfoByIdLiveData(uploadWorkerRequest.id)
        // 使用LiveData监听，必须observe()观察;;
        workInfoLiveData.observe(this, Observer { workInfo: WorkInfo? ->
            if (workInfo != null) {
                val progress = workInfo.progress
                val value = progress.getInt("progress", -1)
                // Do something with progress information
                printLog("clickBtn3() progress=$value")
            }
        })
        // 以下调用会返回null
//        workInfoLiveData.value
        // 如果要判断是否有workInfo，请使用getWorkInfoById()
        val workInfoById = WorkManager.getInstance(applicationContext)
            .getWorkInfoById(uploadWorkerRequest.id)
        val worker = workInfoById.get() ?: return
//        when(worker.state){
//            WorkInfo.State.ENQUEUED -> TODO()
//            WorkInfo.State.RUNNING -> TODO()
//            WorkInfo.State.SUCCEEDED -> TODO()
//            WorkInfo.State.FAILED -> TODO()
//            WorkInfo.State.BLOCKED -> TODO()
//            WorkInfo.State.CANCELLED -> TODO()
//        }

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
