package com.raise.practice.model

import org.fourthline.cling.model.meta.Device
import org.fourthline.cling.model.meta.Service

/**
 * Created by raise.yang on 20/01/08.
 */
class DeviceWrapper(private var device: Device<*, Device<*, *, *>, Service<*, *>>) {

    fun name(): String {
        return device.details.friendlyName
    }

    override fun toString(): String {
        return "friendlyName:" + device.details.friendlyName +
                "\nmanufacturer:" + device.details.manufacturerDetails.manufacturer +
                "\nmodelDetails:" + device.details.modelDetails.modelName +
                "\nidentity:" + device.identity +
                "\nservices:" + device.services[0].serviceType +
                "\ntype:" + device.type +
                "\nversion:" + device.version.major + "." + device.version.minor

    }

}
