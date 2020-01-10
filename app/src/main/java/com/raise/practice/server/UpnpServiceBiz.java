package com.raise.practice.server;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.abupdate.common.ContextVal;
import com.abupdate.common.Trace;
import com.raise.practice.inter.UpnpRegistryListener;
import com.raise.practice.service.UpnpDeviceService;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.controlpoint.SubscriptionCallback;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.model.types.UDN;
import org.fourthline.cling.transport.Router;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * UPNP服务类
 * 1.封装AndroidUpnpService的操作
 * 2.开启服务
 * 3.关闭服务
 * 4.搜索UPNP设备
 *
 * @author hubing
 * @version 1.0.0 2015-4-27
 */

public class UpnpServiceBiz {

    private static String TAG = UpnpServiceBiz.class.getSimpleName();
    private static UpnpServiceBiz localUpnpService;

    private AndroidUpnpService upnpService;
    private Context sContext;

    private UpnpRegistryListener listener;
    // UPNP设备：自己，远程设备
    private ConcurrentHashMap<UDN, LocalDevice> localDevices;

    private UpnpServiceBiz() {
        sContext = ContextVal.getContext();
    }

    public void openUpnpService() {
        // 开启UPNP android service
        Intent service = new Intent(sContext, UpnpDeviceService.class);
        sContext.bindService(service, conn, Service.BIND_AUTO_CREATE);
        localDevices = new ConcurrentHashMap<UDN, LocalDevice>();
    }

    public void closeUpnpService() {
        if (upnpService != null) {
            upnpService.getRegistry().removeAllLocalDevices();
            try {
                sContext.unbindService(conn);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    public static UpnpServiceBiz newInstance() {
        if (localUpnpService == null) {
            localUpnpService = new UpnpServiceBiz();
        }
        return localUpnpService;
    }

    /**
     * UPNP服务开启后，会收到很多device add,remove，通过该监听，可以收到这些信息
     *
     * @param listener
     */
    public void addListener(UpnpRegistryListener listener) {
        if (upnpService != null) {
            upnpService.getRegistry().addListener(listener);
        } else {
            this.listener = listener;
        }
    }

    /**
     * 添加本地设备:添加的设备，立马会被自己的UPNP发现；
     * 1.同时被远程的UPNP服务发现
     *
     * @param localDevice
     */
    public void addDevice(LocalDevice localDevice) {
        Trace.d(TAG, "addDevice() " + localDevice.getDisplayString());
        if (upnpService != null && localDevice.getIdentity() != null) {
            upnpService.getRegistry().addDevice(localDevice);
        }
        localDevices.put(localDevice.getIdentity().getUdn(), localDevice);
    }

    /**
     * 移除UPNP设备
     *
     * @param localDevice
     * @return
     */
    public boolean removeDevice(LocalDevice localDevice) {
        return upnpService.getRegistry().removeDevice(localDevice);
    }

    public Collection<Device> getDevices() {
        return upnpService.getRegistry().getDevices();
    }

    public Collection<Device> getDevices(DeviceType deviceType) {
        return upnpService.getRegistry().getDevices(deviceType);
    }

    public Collection<Device> getDevices(ServiceType serviceType) {
        return upnpService.getRegistry().getDevices(serviceType);
    }

    public Router getRouter() {
        return upnpService.get().getRouter();
    }

    public void search() {
//        for (Entry<UDN, LocalDevice> entry : localDevices.entrySet()) {
//            Trace.d(TAG, "search() 添加本地设备" + entry.getValue().getDisplayString());
//            addDevice(entry.getValue());
//        }
//        // 添加已知设备
//        for (Device device : upnpService.getRegistry().getDevices()) {
//            Trace.d(TAG, "search() 添加已知设备" + device.getDisplayString());
//            listener.deviceAdded(device);
//        }
        Trace.d(TAG, "search() upnpService.getControlPoint().search()开始搜索");
        upnpService.getControlPoint().search();
    }

    public void search(UpnpHeader searchType) {
        upnpService.getControlPoint().search(searchType);
    }

    public void search(int mxSeconds) {
        Trace.d(TAG, "search() mxSeconds=" + mxSeconds);
        upnpService.getControlPoint().search(mxSeconds);
    }

    public void search(UpnpHeader searchType, int mxSeconds) {
        upnpService.getControlPoint().search(searchType, mxSeconds);
    }

    public void sendMsg() {

    }

    public Future execute(ActionCallback callback) {
        return upnpService.getControlPoint().execute(callback);
    }

    public void execute(SubscriptionCallback callback) {
        upnpService.getControlPoint().execute(callback);
    }

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            upnpService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Trace.w(TAG, "onServiceConnected() 开启UNPN服务成功");
            upnpService = (AndroidUpnpService) service;
            if (listener != null) {
                upnpService.getRegistry().addListener(listener);

            }
        }
    };

}
