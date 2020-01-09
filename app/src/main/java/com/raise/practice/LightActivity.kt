package com.raise.practice

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abupdate.common.Trace
import com.abupdate.common_ui.AbTestLayout
import com.raise.practice.model.DeviceUtil
import com.raise.practice.server.UpnpServiceBiz
import kotlinx.android.synthetic.main.activity_light.*
import java.util.*


class LightActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LightActivity"
    }

    private val upnpService: UpnpServiceBiz = UpnpServiceBiz.newInstance()

    inner class TestHelper : AbTestLayout.Helper {
        val BTN1_TEXT = "监听router"
        val BTN2_TEXT = "广播消息"
        override fun onClick(p0: String?) {
            when (p0) {
                BTN1_TEXT -> {
                    print(BTN1_TEXT)
                    upnpService.router.enable()
                    print("router:" + upnpService.router.isEnabled)
//                    upnpService.router.received()
                }
                BTN2_TEXT -> {
                    print(BTN2_TEXT)
                    upnpService.router.broadcast(
                            ("来自" + Build.MANUFACTURER + "的一条广播消息").toByteArray())
                }
            }
        }

        override fun getBtnTexts(): ArrayList<String> {
            return arrayListOf(BTN1_TEXT)
        }

        override fun print(p0: String?) {
            test_layout.printToScreen(p0)
        }

    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light)

        test_layout.loadTest(TestHelper())
        Trace.d(TAG, "onCreate() upnpService.addDevice(createDevice())")
        val device = DeviceUtil.createDevice()
        upnpService.addDevice(device)
    }

}
