package com.raise.practice.work

import android.content.Context
import android.os.SystemClock
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.raise.weapon_base.LLog

/**
 * 定义一个worker
 * 定义工作后，必须使用 WorkManager 服务进行调度该工作才能运行。
 */
class UploadWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    private val TAG = "UploadWorker"

    /**
     * 重写doWork()
     */
    override fun doWork(): Result {
        uploadFiles()

        inputData.keyValueMap["xx"]

        return Result.success()
    }

    private fun uploadFiles() {

        LLog.d(TAG, "uploadFiles() 模拟上传文件，sleep(5000)")
        SystemClock.sleep(5000)

    }
}