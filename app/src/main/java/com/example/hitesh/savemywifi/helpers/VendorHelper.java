package com.example.hitesh.savemywifi.helpers;

/**
 * Created by mayank on 20/2/18.
 */
import android.content.Context;
import android.util.Base64;
import com.example.hitesh.savemywifi.Device;

public class VendorHelper
{
    public static final String CONTENT_KEY_2 = "Y2EtYXBwLXB1Yi00Nzc5MDg0MzkwMDQ3NDQ2LzUwNzQyNzUzOTI=";

    public static String getContent(String paramString)
    {
        return new String(Base64.decode(paramString, 0));
    }

//    public static int getIconFromDevice(Context paramContext, Device paramDevice)
//    {
//        if (paramDevice.mIconTypeResources != 0) {
//            return paramDevice.mIconTypeResources;
//        }
//        String str = new SQLiteOperationsHelper(paramContext).getIconStringFromMac(getStripedMacAddress(paramDevice.mMacAddress));
//        int i = -1;
//        switch (str.hashCode())
//        {
//        }
//        for (;;)
//        {
//            switch (i)
//            {
//                default:
//                    return 2130837661;
//                case 0:
//                    return 2130837659;
//                if (str.equals("phone"))
//                {
//                    i = 0;
//                    continue;
//                    if (str.equals("computer"))
//                    {
//                        i = 1;
//                        continue;
//                        if (str.equals("nintendo"))
//                        {
//                            i = 2;
//                            continue;
//                            if (str.equals("playstation"))
//                            {
//                                i = 3;
//                                continue;
//                                if (str.equals("apple"))
//                                {
//                                    i = 4;
//                                    continue;
//                                    if (str.equals("samsung")) {
//                                        i = 5;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                break;
//            }
//        }
//        return 2130837647;
//        return 2130837652;
//        return 2130837654;
//        return 2130837646;
//        return 2130837656;
//    }

    public static String getStripedMacAddress(String paramString)
    {
        if (paramString.contains(":"))
        {
            String str = paramString.replace(":", "");
            paramString = str.substring(0, Math.min(6, str.length()));
        }
        return paramString;
    }

    public static String getVendorFromDevice(Context paramContext, Device paramDevice)
    {
        SQLiteOperationsHelper sqLiteOperationsHelper= new SQLiteOperationsHelper(paramContext);
        String str=sqLiteOperationsHelper.getVendorFromMac(getStripedMacAddress(paramDevice.mac));
        return str;
    }
}