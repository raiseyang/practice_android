package com.raise.practice

import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.abupdate.common.Dispatcher
import com.abupdate.common.ThreadUtil
import com.abupdate.common.Trace
import com.abupdate.common_ui.AbToast
import com.abupdate.common_ui.dialog.AbDialog
import com.abupdate.common_ui.dialog.IBuilder
import com.abupdate.common_ui.dialog.IVCDialog

/**
 * Created by raise.yang on 19/01/24.
 */


fun AppCompatActivity.showAbDialog(block: IBuilder.() -> IBuilder): IVCDialog {
    val builder = block(AbDialog.Builder(this))
    val dialog = builder.create()
    dialog.show()
    return dialog
}

fun AppCompatActivity.cancelAbDialog(d: IVCDialog?) {
    d?.let {
        if (d.isShowing) {
            d.dismiss()
        }
    }
}

/**
 * 获取资源String
 */
fun getStringEx(resInt: Int, vararg arg1: String): String {
    return if (arg1.isEmpty())
        App.context.resources.getString(resInt)
    else
        String.format(App.context.resources.getString(resInt), arg1)
}

/**
 * 通知UI线程干活
 */
fun postUI(delayTimeInMills: Long = 0L, block: () -> Unit) {
    ThreadUtil.postUI(delayTimeInMills, block)
}

/**
 * 通知UI线程干活
 */
fun androidx.fragment.app.Fragment.postUI(delayTimeInMills: Long = 0L, block: () -> Unit) {
    if (delayTimeInMills == 0L) {
        if (lifecycle.currentState == Lifecycle.State.CREATED
                || lifecycle.currentState == Lifecycle.State.STARTED
                || lifecycle.currentState == Lifecycle.State.RESUMED
        ) {
            ThreadUtil.postUI(delayTimeInMills, block)
        }
    } else {
        ThreadUtil.postUI(delayTimeInMills) {
            if (lifecycle.currentState == Lifecycle.State.CREATED
                    || lifecycle.currentState == Lifecycle.State.STARTED
                    || lifecycle.currentState == Lifecycle.State.RESUMED
            ) {
                ThreadUtil.postUI(block)
            }
        }
    }
}

/**
 * 通知UI线程干活
 */
fun AppCompatActivity.postUI(delayTimeInMills: Long = 0L, block: () -> Unit) {
    if (delayTimeInMills == 0L) {
        if (lifecycle.currentState == Lifecycle.State.CREATED
                || lifecycle.currentState == Lifecycle.State.STARTED
                || lifecycle.currentState == Lifecycle.State.RESUMED
        ) {
            ThreadUtil.postUI(delayTimeInMills, block)
        }
    } else {
        ThreadUtil.postUI(delayTimeInMills) {
            if (lifecycle.currentState == Lifecycle.State.CREATED
                    || lifecycle.currentState == Lifecycle.State.STARTED
                    || lifecycle.currentState == Lifecycle.State.RESUMED
            ) {
                ThreadUtil.postUI(block)
            }
        }
    }
}

fun async(delayTimeInMills: Long = 0L, block: () -> Unit) {
    Dispatcher.with().enqueue {
        if (delayTimeInMills > 0) {
            SystemClock.sleep(delayTimeInMills)
        }
        block()
    }
}

fun toast(text: String) {
    AbToast.show(text)
}

fun trace(content: String) {
    Trace.d("//ktEx//", content)
}