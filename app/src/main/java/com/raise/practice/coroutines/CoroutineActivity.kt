package com.raise.practice.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.abupdate.common.Trace
import com.raise.practice.R
import com.raise.practice.postUI
import com.raise.practice.toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

class CoroutineActivity : AppCompatActivity() {

    companion object {
        const val TAG = "CoroutineActivity"
    }

    val crt = GlobalScope.launch {
        repeat(1000) {
            kotlinx.coroutines.delay(500)
            Trace.d(TAG, "repeat() $it")
        }
        newSingleThreadContext("333").use {  }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Trace.d(TAG, "onCreate() ")
        setContentView(R.layout.activity_coroutine)
//        GlobalScope.launch {
//            crt.join()
//        }
    }

    override fun onDestroy() {
        Trace.d(TAG, "onDestroy() ")
        GlobalScope.launch {
            crt.cancel()
        }

        super.onDestroy()
    }

    fun click_one(view: View) {
        toast("点击one")
        crt.cancel()
        postUI(2000) {
            GlobalScope.launch {
                crt.join()
            }
        }


//        Trace.d(TAG, "click_one() start")
//        GlobalScope.launch(Dispatchers.Main) {
//            withContext(Dispatchers.IO) {
//                Trace.d(TAG, "click_one() withContext thread.name=${Thread.currentThread().name}")
//            }
//            Trace.d(TAG, "click_one() thread.name=${Thread.currentThread().name}")
//        }
//        Trace.d(TAG, "click_one() end")
    }
}
