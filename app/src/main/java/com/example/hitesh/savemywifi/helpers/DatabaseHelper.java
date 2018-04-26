package com.example.hitesh.savemywifi.helpers;

/**
 * Created by mayank on 20/2/18.
 */
import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper
        extends SQLiteAssetHelper
{
    private static final String DATABASE_NAME = "vendors.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_VENDORS = "macvendor";
    public static final String VENDORS_MAC = "mac";
    public static final String VENDORS_TYPE = "type";
    public static final String VENDORS_VENDOR = "vendor";
    private static DatabaseHelper mInstance;

    private DatabaseHelper(Context paramContext)
    {
        super(paramContext, "vendors.db", null, 1);
    }

    public static DatabaseHelper getInstance(Context paramContext)
    {
        try
        {
            if (mInstance == null) {
                mInstance = new DatabaseHelper(paramContext);
            }
            DatabaseHelper localDatabaseHelper = mInstance;
            return localDatabaseHelper;
        }
        finally {}
    }
}
