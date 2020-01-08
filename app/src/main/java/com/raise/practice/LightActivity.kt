package com.raise.practice

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abupdate.common.Trace
import com.abupdate.common_ui.AbTestLayout
import com.raise.practice.server.UpnpServiceBiz
import com.raise.practice.upnpserver.ContentDirectoryService
import kotlinx.android.synthetic.main.activity_light.*
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder
import org.fourthline.cling.model.DefaultServiceManager
import org.fourthline.cling.model.meta.*
import org.fourthline.cling.model.types.UDADeviceType
import org.fourthline.cling.model.types.UDN
import java.io.IOException
import java.util.*


class LightActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LightActivity"
    }

    private val upnpService: UpnpServiceBiz = UpnpServiceBiz.newInstance()

    inner class TestHelper : AbTestLayout.Helper {
        override fun onClick(p0: String?) {
            when (p0) {
                "打开设备" -> {
                    print("打开设备")

                }
            }
        }

        override fun getBtnTexts(): ArrayList<String> {
            return arrayListOf("打开设备")
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
        upnpService.addDevice(createDevice())
    }

    protected fun createDevice(): LocalDevice {
        val type = UDADeviceType("BinaryLight", 1)

        val details = DeviceDetails("HISENCE A5:" + Build.DEVICE,
                ManufacturerDetails("HISENCE"),
                ModelDetails("AndroidLight",
                        "A light with on/off switch.",
                        "v1")
        )
        val udn = UDN(UUID.randomUUID())

        val service = AnnotationLocalServiceBinder().read(ContentDirectoryService::class.java)
        service.setManager(DefaultServiceManager<ContentDirectoryService>(service as LocalService<ContentDirectoryService>?, ContentDirectoryService::class.java))

        Trace.d(TAG, "createDevice() " + udn)
        return LocalDevice(
                DeviceIdentity(udn),
                type,
                details,
                createDefaultDeviceIcon(),
                service
        )
    }

    fun createDefaultDeviceIcon(): Icon? {
        try {
            return Icon("image/png", 48, 48, 32,
                    "msi.png", getResources().getAssets()
                    .open("ic_launcher.png"))
        } catch (e: IOException) {
            Trace.e(TAG, "createDefaultDeviceIcon() ")
            return null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
