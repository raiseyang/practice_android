package com.raise.practice.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay

/**
 * 使用协程的Worker
 * 继承自：[CoroutineWorker]
 */
class UploadCoroutineWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {


    override suspend fun doWork(): Result {
        // 更新进度，view层可以观察该数据
        val firstUpdate = workDataOf("progress" to 0)
        val lastUpdate = workDataOf("progress" to 100)
        // setProgress：可以传输work中的进度或状态信息，参数使用workDataOf()构建
        setProgress(firstUpdate)
        repeat(10) {
            setProgress(workDataOf("progress" to it + 1))
            delay(1000)
        }
        setProgress(lastUpdate)
        // 返回work结果，注意实测中发现，当返回Result之后，setProgress()中的数据就没有了，获取不到了
        return Result.success()
    }


}