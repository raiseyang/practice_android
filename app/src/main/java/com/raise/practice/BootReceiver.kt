package com.raise.practice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.abupdate.common.Trace

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Trace.d("BootReceiver", "receive boot!")
    }
}