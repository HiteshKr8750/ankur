package com.example.hitesh.savemywifi.helpers;

/**
 * Created by mayank on 20/2/18.
 */
import android.content.Context;

import android.net.wifi.WifiManager;

import com.example.hitesh.savemywifi.R;

import io.evercam.network.discovery.IpTranslator;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class NetworkInfoHelper
{
    public WifiManager mWifiManager;
    public String s_BSSID;
    public String s_SSID;
    public String s_dns1;
    public String s_dns2;
    public String s_gateway;
    public String s_ipAddress;
    public boolean s_isHidden;
    public int s_leaseDuration;
    public String s_netmask;
    public String s_serverAddress;

    public NetworkInfoHelper(Context paramContext)
    {
        this.mWifiManager = ((WifiManager)paramContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE));
        this.s_dns1 = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().dns1);
        this.s_dns2 = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().dns2);
        this.s_gateway = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().gateway);
        this.s_ipAddress = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().ipAddress);
        this.s_leaseDuration = (1000 * this.mWifiManager.getDhcpInfo().leaseDuration);
        this.s_netmask = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().netmask);
        this.s_serverAddress = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().serverAddress);
        this.s_SSID = this.mWifiManager.getConnectionInfo().getSSID();
        this.s_SSID = this.s_SSID.replaceAll("^\"|\"$", "");
        this.s_BSSID = this.mWifiManager.getConnectionInfo().getBSSID();
        this.s_isHidden = this.mWifiManager.getConnectionInfo().getHiddenSSID();
    }

    public String getInterfaceMacAddress(Context paramContext)
    {
        String str1 = paramContext.getString(R.string.unknown_mac_address);
        try
        {
            Iterator localIterator = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
            while (localIterator.hasNext())
            {
                NetworkInterface localNetworkInterface = (NetworkInterface)localIterator.next();
                if (localNetworkInterface.getName().equalsIgnoreCase("wlan0"))
                {
                    byte[] arrayOfByte = localNetworkInterface.getHardwareAddress();
                    if (arrayOfByte == null) {
                        return "";
                    }
                    StringBuilder localStringBuilder = new StringBuilder();
                    int i = arrayOfByte.length;
                    for (int j = 0; j < i; j++)
                    {
                        int k = arrayOfByte[j];
                        localStringBuilder.append(Integer.toHexString(k & 0xFF) + ":");
                    }
                    if (localStringBuilder.length() > 0) {
                        localStringBuilder.deleteCharAt(-1 + localStringBuilder.length());
                    }
                    String str2 = localStringBuilder.toString();
                    return str2;
                }
            }
            return str1;
        }
        catch (Exception localException) {}
        return paramContext.getString(R.string.unknown_mac_address);
    }

    public int getLinkSpeed()
    {
        return this.mWifiManager.getConnectionInfo().getLinkSpeed();
    }
}
