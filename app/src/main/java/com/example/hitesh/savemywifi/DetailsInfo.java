package com.example.hitesh.savemywifi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import io.evercam.network.discovery.IpTranslator;

/**
 * Created by Hitesh on 2/14/2018.
 */

public class DetailsInfo extends Activity {
    public WifiManager mWifiManager;
    public String s_BSSID;
    public String s_SSID;
    public String s_dns1;
    public String s_dns2;
    public String s_gateway;
    public String s_ipAddress;

    public int s_leaseDuration;
    public String s_netmask;
    private TextView dns_1;
    private TextView ip_addr;
    private TextView dns_2;
    private TextView g_way;
    private TextView net_mask;
    private TextView dns_value;
    private TextView lease_duration;
    private TextView bssid;
    private TextView ss_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_details);
        intiUI();
        this.mWifiManager = ((WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE));
        this.s_dns1 = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().dns1);
        dns_1.setText(s_dns1);
        this.s_dns2 = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().dns2);
        dns_2.setText(s_dns2);
        this.s_gateway = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().gateway);
        g_way.setText(s_gateway);
        this.s_ipAddress = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().ipAddress);
        ip_addr.setText(s_ipAddress);
//  this.s_leaseDuration = (1000 * this.mWifiManager.getDhcpInfo().leaseDuration);
//        lease_duration.setText(s_leaseDuration);
      this.s_netmask = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().netmask);
        net_mask.setText(s_netmask);
        this.s_BSSID = this.mWifiManager.getConnectionInfo().getBSSID();
        bssid.setText(s_BSSID);
        this.s_SSID = this.mWifiManager.getConnectionInfo().getSSID();
        this.s_SSID = this.s_SSID.replaceAll("^\"|\"$", "");
        ss_id.setText(s_SSID);
        ImageView info = (ImageView) findViewById(R.id.info_cursor);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsInfo.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //Menu code
        final ImageView dinfo = (ImageView) findViewById(R.id.setting_details);
        dinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(DetailsInfo.this, dinfo);
                popup.getMenuInflater().inflate(R.menu.action_menu_details, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(DetailsInfo.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popup.show();//showing popup menu
            }
        });

    }

//    public String getInterfaceMacAddress(Context paramContext)
//    {
////        String str1 = paramContext.getString("");
//        try
//        {
//            Iterator localIterator = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
//            while (localIterator.hasNext())
//            {
//                NetworkInterface localNetworkInterface = (NetworkInterface)localIterator.next();
//                if (localNetworkInterface.getName().equalsIgnoreCase("wlan0"))
//                {
//                    byte[] arrayOfByte = localNetworkInterface.getHardwareAddress();
//                    if (arrayOfByte == null) {
//                        return "";
//                    }
//                    StringBuilder localStringBuilder = new StringBuilder();
//                    int i = arrayOfByte.length;
//                    for (int j = 0; j < i; j++)
//                    {
//                        int k = arrayOfByte[j];
//                        localStringBuilder.append(Integer.toHexString(k & 0xFF) + ":");
//                    }
//                    if (localStringBuilder.length() > 0) {
//                        localStringBuilder.deleteCharAt(-1 + localStringBuilder.length());
//                    }
//                    String str2 = localStringBuilder.toString();
//                    return str2;
//                }
//            }
////            return str1;
////        }
//        catch (Exception localException) {}
//        return paramContext.getString(2131165293);
//    }

    private void intiUI() {
        ip_addr = (TextView) findViewById(R.id.ipadr_value);
        dns_1 = (TextView) findViewById(R.id.dns_value);
        dns_2 = (TextView) findViewById(R.id.dns_value2);
        g_way = (TextView) findViewById(R.id.gate_way);
        net_mask = (TextView) findViewById(R.id.net_mask);
        lease_duration = (TextView) findViewById(R.id.leasetime);
        bssid = (TextView) findViewById(R.id.bss_id);
        ss_id =(TextView) findViewById(R.id.wifiname);
    }

}
