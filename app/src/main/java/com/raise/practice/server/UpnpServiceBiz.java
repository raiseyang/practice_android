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
import org.fourthline.cling.model.DiscoveryOptions;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.model.types.UDN;
import org.fourthline.cling.transport.Router;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * @author hubing
 * @version 1.0.0 2015-4-27
 */

public class UpnpServiceBiz {

    protected static String TAG = UpnpServiceBiz.class.getSimpleName();
    private Context sContext;

    protected AndroidUpnpService upnpService;
    private static UpnpServiceBiz localUpnpService;

    private UpnpRegistryListener listener;

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

    public void addListener(UpnpRegistryListener listener) {
        if (upnpService != null) {
            upnpService.getRegistry().addListener(listener);
        } else {
            this.listener = listener;
        }
    }

    public void removeListener(UpnpRegistryListener listener) {
        upnpService.getRegistry().removeListener(listener);
    }

    public void removeAllRemoteDevices() {
        upnpService.getRegistry().removeAllRemoteDevices();
    }

    /**
     * 添加本地设备
     *
     * @param localDevice
     */
    public void addDevice(LocalDevice localDevice) {
        Trace.d(TAG, "addDevice() " + localDevice.getDisplayString());
        if (upnpService != null && localDevice.getIdentity() != null) {
            upnpService.getRegistry().addDevice(localDevice);
        }
        addDevice(localDevice.getIdentity().getUdn(), localDevice);
    }

    /**
     * 添加本地设备
     *
     * @param localDevice
     * @param options
     */
    public void addDevice(LocalDevice localDevice, DiscoveryOptions options) {
        if (upnpService != null && localDevice.getIdentity() != null) {
            upnpService.getRegistry().addDevice(localDevice, options);
        }
        addDevice(localDevice.getIdentity().getUdn(), localDevice);
    }

    /**
     * 添加本地设备到设备列表
     *
     * @param device
     */
    private void addDevice(UDN udn, LocalDevice device) {
        localDevices.put(udn, device);
    }

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
        for (Entry<UDN, LocalDevice> entry : localDevices.entrySet()) {
            Trace.d(TAG, "search() 添加本地设备" + entry.getValue().getDisplayString());
            addDevice(entry.getValue());
        }
        // 添加已知设备
        for (Device device : upnpService.getRegistry().getDevices()) {
            Trace.d(TAG, "search() 添加已知设备" + device.getDisplayString());
            listener.deviceAdded(device);
        }
        Trace.d(TAG, "search() upnpService.getControlPoint().search()开始搜索");
        upnpService.getControlPoint().search();
    }

    public void search(UpnpHeader searchType) {
        upnpService.getControlPoint().search(searchType);
    }

    public void search(int mxSeconds) {
        upnpService.getControlPoint().search(mxSeconds);
    }

    public void search(UpnpHeader searchType, int mxSeconds) {
        upnpService.getControlPoint().search(searchType, mxSeconds);
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
