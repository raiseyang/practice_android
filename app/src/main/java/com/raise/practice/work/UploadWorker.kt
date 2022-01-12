package com.raise.practice.work

import android.content.Context
import android.os.SystemClock
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.raise.weapon_base.LLog

/**
 * 定义一个worker
 * 定义工作后，必须使用 WorkManager 服务进行调度该工作才能运行。
 *
 * 非协程Worker取消后，doWork()中的代码依旧正常执行。需要在其中通过isStopped来中止;或重写onStopped()来监听
 */
class UploadWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    private val TAG = "UploadWorker"

    /**
     * 重写doWork()
     */
    override fun doWork(): Result {
        uploadFiles()

        LLog.d(TAG, "doWork() inputData.keyValueMap[\"key1\"]=${inputData.keyValueMap["key1"]}")

        return Result.success()
    }

    private fun uploadFiles() {

        for (i in 1..100) {

            LLog.d(TAG, "uploadFiles() i=$i")

            SystemClock.sleep(1000)

            if (isStopped) {
                LLog.d(TAG, "uploadFiles() 我stop了 this=$this")
                break
            }
        }

    }

    override fun onStopped() {
        super.onStopped()
    }
}