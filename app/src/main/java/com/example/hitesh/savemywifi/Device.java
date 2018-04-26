package com.example.hitesh.savemywifi;

import android.content.Context;

import com.example.hitesh.savemywifi.helpers.VendorHelper;
import com.stealthcopter.networktools.ARPInfo;

/**
 * Created by Hitesh on 2/19/2018.
 */

public class Device  {
    public String ip,mac,vendor;
     public Device(){

    }

    public Device(String paramString, Context paramContext)
    {
        this.ip = paramString;
        this.mac = setMacAddressAndDescription(paramString, paramContext);
//        this.vendor= VendorHelper.getVendorFromDevice(paramContext,Device.this);
    }

    private String setMacAddressAndDescription(String paramString, Context paramContext) {
        String str = ARPInfo.getMACFromIPAddress(paramString);
         return str;
    }

    public String getIp() {
        return ip;
    }

    public String getMac() {
        return mac;
    }

    public String getVendor() {
        return vendor;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}
