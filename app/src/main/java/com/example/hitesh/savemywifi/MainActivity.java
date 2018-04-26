package com.example.hitesh.savemywifi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.stealthcopter.networktools.ARPInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.evercam.network.discovery.IpScan;
import io.evercam.network.discovery.IpTranslator;
import io.evercam.network.discovery.ScanRange;
import io.evercam.network.discovery.ScanResult;


public class MainActivity extends AppCompatActivity {
    public WifiManager mWifiManager;
    public String s_gateway;
    public String s_netmask;
    private String ssid;
    private TextView number;
    private TextView ssid_name;
    private TextView ipadrs;
    private TextView macadr;
    private ImageView img_button;
    private InterstitialAd mInterstitialAd;
    private RecyclerView recyclerView;
    private DevicesAdapter devicesAdapter;
    private List<Device> deviceList = new ArrayList<>();
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiUI();
        setListeners();
        getApplicationContext();
        getCurrentSsid(MainActivity.this);
        getGateway(MainActivity.this);
        getMask(MainActivity.this);
    }

    private void setListeners() {
        //mobile ads
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        img_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //add loaded on button click
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                        start_scan();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");

                    }
                    // scaning routers


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
//ads function

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("Ads", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Ads", "onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.i("Ads", "onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.i("Ads", "onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
//                recyclerView.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.INVISIBLE);

            }
        });


//setting or info
        ImageView info = (ImageView) findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailsInfo.class);
                startActivity(intent);
            }
        });

        //Menu code
        final ImageView img = (ImageView) findViewById(R.id.setting);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = null;
                PopupMenu popup = new PopupMenu(MainActivity.this, img);
                popup.getMenuInflater().inflate(R.menu.actionmenu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.about) {
                            Intent intent = new Intent(MainActivity.this, AboutUs.class);
                            startActivity(intent);
                        } else if (item.getItemId() == R.id.contact) {

                            Toast.makeText(context, "LoginUser has loaded!", Toast.LENGTH_LONG).show();
                            return true;
                        }
                        return false;
                    }

                });
                popup.show();//showing popup menu
            }
        });
    }

    private void start_scan() {
        PowerManager.WakeLock localWakeLock = ((PowerManager)getSystemService(POWER_SERVICE)).newWakeLock(1, "WifiScanLockLock");
        localWakeLock.acquire();
        try {
            ScanRange scanRange = new ScanRange(getGateway(MainActivity.this),getMask(MainActivity.this));
            final int i = scanRange.size();
            IpScan ipScan=new IpScan(new ScanResult() {
                private int count = 0;
                @Override
                public void onActiveIp(String s) {

                }

                @Override
                public void onIpScanned(String s) {
                    this.count = (1 + this.count);
                }
            });
            ipScan.scanAll(scanRange);
            ArrayList localArrayList1 = ARPInfo.getAllIPAddressesInARPCache();
            localArrayList1.add(this.getIpAddress(this));
            deviceList = getDevicesFromStrings(localArrayList1);
            devicesAdapter = new DevicesAdapter(deviceList);
            recyclerView.setAdapter(devicesAdapter);
            number.setText(String.valueOf(deviceList.size()));
            updateUI();
            localWakeLock.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUI() {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

    }

    public ArrayList<Device> getDevicesFromStrings(ArrayList<String> paramArrayList)
    {
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = paramArrayList.iterator();
        while (localIterator.hasNext()) {
            localArrayList.add(new Device((String)localIterator.next(), MainActivity.this));
        }
        return localArrayList;
    }

    private String getGateway(Context context) {
        this.mWifiManager = ((WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE));
        this.s_gateway = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().gateway);
        return s_gateway;

    }

    private String getMask(Context context) {
        this.mWifiManager = ((WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE));
        this.s_netmask = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().netmask);
        return s_netmask;

    }

    private void intiUI() {
        ssid_name = (TextView) findViewById(R.id.routername);
        ipadrs = (TextView) findViewById(R.id.ipAddress);
        macadr = (TextView) findViewById(R.id.macAdress);
        img_button = (ImageView) findViewById(R.id.image_button);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_id);
        devicesAdapter = new DevicesAdapter(deviceList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        number=(TextView)findViewById(R.id.number_of_devices);
    }



    public String getIpAddress(Context context){
        String ip_address;
        WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        ip_address=IpTranslator.getIpFromIntSigned(manager.getDhcpInfo().ipAddress);

        return ip_address;
    }

    public void getCurrentSsid(Context context) {

        WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getBSSID();
        macadr.setText(address);

        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
                ssid = connectionInfo.getSSID();
                ssid_name.setText(ssid);
                //ip Adress
                int ip = connectionInfo.getIpAddress();
                String ipAddress = Formatter.formatIpAddress(ip);
                ipadrs.setText(ipAddress);


            }
        }
    }
}
