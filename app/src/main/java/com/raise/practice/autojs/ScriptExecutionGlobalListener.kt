package com.raise.practice.autojs

import com.abupdate.common.Trace
import com.stardust.autojs.execution.ScriptExecution
import com.stardust.autojs.execution.ScriptExecutionListener

/**
 * Created by Stardust on 2017/5/3.
 */

class ScriptExecutionGlobalListener : ScriptExecutionListener {

    override fun onStart(execution: ScriptExecution) {
        execution.engine.setTag(ENGINE_TAG_START_TIME, System.currentTimeMillis())
        Trace.d(TAG, "onStart() scriptName=${execution.source.name}")
    }

    override fun onSuccess(execution: ScriptExecution, result: Any?) {
        onFinish(execution)
    }

    private fun onFinish(execution: ScriptExecution) {
        val millis = execution.engine.getTag(ENGINE_TAG_START_TIME) as Long? ?: return
        val seconds = (System.currentTimeMillis() - millis) / 1000.0
        AutoJs.instance.scriptEngineService.globalConsole
                .verbose(TAG, execution.source.toString(), seconds)
    }

    override fun onException(execution: ScriptExecution, e: Throwable) {
        Trace.e(TAG, "onException() $e")
        onFinish(execution)
    }

    companion object {
        private const val ENGINE_TAG_START_TIME = "start_time"
        private const val TAG = "ScriptExecutionGlobalListener"

    }

}
