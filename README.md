# UPNP分支

1. 实现设备被其他设备发现；
2. 实现发现其他设备；
3. 不能广播消息给其他设备，其他设备也不能接收广播消息；

> 我们业务想要的场景是设备间互相发现，能够广播消息给其他设备，能够点对点发送消息，好像UPNP协议不是为此而生的；

friendlyName:Build.DEVICE::HLTE202N
    manufacturer:Hisense
    modelDetails:HLTE202N
    identity:(DeviceIdentity) UDN: uuid:5686b37b-ae57-476d-9b82-111111111111
    services:urn:schemas-upnp-org:service:ContentDirectory:1
    type:urn:schemas-upnp-org:device:phone:1
    version:1.0

friendlyName:xxCAE5A-C_0374
    manufacturer:Hisilicon Technologies Co.,Ltd
    modelDetails:Hisilicon HiMultiScreen
    identity:(RemoteDeviceIdentity) UDN: uuid:cc6g32df-aaaa-22c3-e029-803896EEAB51, Descriptor: http://172.18.7.135:49152/description.xml
    services:urn:schemas-upnp-org:service:AccessControlServer:1
    type:urn:schemas-upnp-org:device:HiMultiScreenServerDevice:1
    version:1.0

friendlyName:客厅的小米电视
    manufacturer:Xiaomi
    modelDetails:Xiaomi MediaRenderer
    identity:(RemoteDeviceIdentity) UDN: uuid:F7CA5454-3F48-4390-8009-483b46e04919, Descriptor: http://172.18.7.172:49152/description.xml
    services:urn:schemas-upnp-org:service:AVTransport:1
    type:urn:schemas-upnp-org:device:MediaRenderer:1
    version:1.0

friendlyName:夏普果果投屏
    manufacturer:Microsoft Corporation
    modelDetails:Windows Media Player
    identity:(RemoteDeviceIdentity) UDN: uuid:31e0-a168-7871-e04c, Descriptor: http://172.18.7.135:39620/description.xml
    services:urn:schemas-upnp-org:service:RenderingControl:1
    type:urn:schemas-upnp-org:device:MediaRenderer:1
    version:1.0