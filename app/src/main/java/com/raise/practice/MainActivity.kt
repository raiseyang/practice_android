package com.raise.practice

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.abupdate.common.Trace
import com.abupdate.common.UIThreadUtil
import com.abupdate.common_ui.AbTestLayout
import com.abupdate.common_ui.AbToast
import com.raise.practice.inter.UpnpRegistryListener
import com.raise.practice.model.DeviceWrapper
import com.raise.practice.server.UpnpServiceBiz
import com.raise.practice.util.IpUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.fourthline.cling.model.meta.Device
import org.fourthline.cling.model.meta.Service
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private lateinit var upnpServiceBiz: UpnpServiceBiz

    private var simpleAdapter: ArrayAdapter<String?>? = null
    private val deviceNames: MutableList<String?> = mutableListOf()
    private val deviceMap: MutableMap<String?, Device<*, Device<*, *, *>, Service<*, *>>> = mutableMapOf()

    inner class TestHelper : AbTestLayout.Helper {
        override fun onClick(p0: String?) {
            when (p0) {
                "扫描" -> {

                }
            }
        }

        override fun getBtnTexts(): ArrayList<String> {
            return arrayListOf("扫描", "测试2")
        }

        override fun print(p0: String?) {
            test_layout.printToScreen(p0)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        org.seamless.util.logging.LoggingUtil.resetRootHandler(
//                FixedAndroidLogHandler()
//        )
//        Logger.getLogger("com.raise.practice").setLevel(Level.ALL)
        Logger.getLogger("org.fourthline.cling").level = Level.FINEST
        Trace.setShowPosition(true)
        //开启服务
        upnpServiceBiz = UpnpServiceBiz.newInstance()
        upnpServiceBiz.addListener(object : UpnpRegistryListener() {
            override fun deviceAdded(device: Device<*, Device<*, *, *>, Service<*, *>>) {
                val deviceWrapper = DeviceWrapper(device)
                Trace.d(TAG, "deviceAdded() $deviceWrapper")
//                if ("MI" in device.displayString) {
//                    Trace.d(TAG, "deviceAdded() ")
//                }
                if (deviceWrapper.name() !in deviceNames) {
                    deviceNames.add(deviceWrapper.name())
                    deviceMap[deviceWrapper.name()] = device
                }
            }

            override fun deviceRemoved(device: Device<*, out Device<*, *, *>, out Service<*, *>>?) {
                Trace.d(TAG, "deviceRemoved() ${device?.displayString}")
                deviceNames.remove(device?.displayString)
            }
        })
        upnpServiceBiz.openUpnpService()

        test_layout.loadTest(TestHelper())
        btn_1.text = "搜索设备"
        btn_1.setOnClickListener { clickBtn1() }

        btn_2.text = "跳转客户端界面"
        btn_2.setOnClickListener {
            clickBtn2()
        }
        btn_3.text = "广播消息"
        btn_3.setOnClickListener {
            clickBtn3()
        }
        btn_4.text = "单点发送消息"
        btn_4.setOnClickListener {
            clickBtn4()
        }

        simpleAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceNames)
        listView.adapter = simpleAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
            AbToast.show("点击：" + deviceNames[position])
            thread {
//                upnpServiceBiz.execute(Play(deviceMap[deviceNames[position]].findService()))
            }
        }
        tv_ip.text = IpUtil.getLocalIpAddress(this)
    }

    private fun clickBtn1() {
        Trace.d(TAG, "clickBtn1() ")
        upnpServiceBiz.search(30)
        var i = 30
        UIThreadUtil.postUI(object : Runnable {
            override fun run() {
                if (i-- > 0) {
                    test_layout.printToScreen("倒计时：$i")
                    UIThreadUtil.postUI(1000, this)
                }
            }
        })
    }

    private fun clickBtn2() {
        Trace.d(TAG, "clickBtn2() ")
        startActivity(Intent(this, LightActivity::class.java))
    }

    private fun clickBtn3() {
        Trace.d(TAG, "clickBtn3() ")
        thread {
            upnpServiceBiz.router.broadcast("来自服务端的广播消息1".toByteArray())
        }
    }

    private fun clickBtn4() {
        Trace.d(TAG, "clickBtn4() ")
    }

    override fun onDestroy() {
        super.onDestroy()
        upnpServiceBiz.closeUpnpService()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
