package com.raise.practice.model

import android.os.Build
import com.abupdate.common.ContextVal
import com.abupdate.common.Trace
import com.raise.practice.LightActivity
import com.raise.practice.upnpserver.ContentDirectoryService
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder
import org.fourthline.cling.model.DefaultServiceManager
import org.fourthline.cling.model.meta.*
import org.fourthline.cling.model.types.UDADeviceType
import org.fourthline.cling.model.types.UDN
import java.io.IOException

/**
 * Created by raise.yang on 20/01/09.
 */
object DeviceUtil {

    const val uuid = "5686b37b-ae57-476d-9b82-111111111111"
    const val deviceType = "phone"

    fun createDevice(): LocalDevice {
        val type = UDADeviceType(deviceType, 1)

        val details = DeviceDetails("Build.DEVICE::" + Build.DEVICE,
                ManufacturerDetails(Build.MANUFACTURER),
                ModelDetails(Build.MODEL,
                        "Build.HARDWARE：" + Build.HARDWARE,
                        "v1")
        )
        val udn = UDN(uuid)

        val service = AnnotationLocalServiceBinder().read(ContentDirectoryService::class.java)
        service.setManager(DefaultServiceManager<ContentDirectoryService>(service as LocalService<ContentDirectoryService>?, ContentDirectoryService::class.java))

        return LocalDevice(
                DeviceIdentity(udn),
                type,
                details,
                createDefaultDeviceIcon(),
                service
        )
    }

    private fun createDefaultDeviceIcon(): Icon? {
        return try {
            Icon("image/png", 48, 48, 32,
                    "msi.png", ContextVal.getContext().getResources().getAssets()
                    .open("ic_launcher.png"))
        } catch (e: IOException) {
            Trace.e(LightActivity.TAG, "createDefaultDeviceIcon() ")
            null
        }
    }

}
