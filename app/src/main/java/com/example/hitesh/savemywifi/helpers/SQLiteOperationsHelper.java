package com.example.hitesh.savemywifi.helpers;

import android.content.Context;

/**
 * Created by mayank on 20/2/18.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hitesh.savemywifi.R;

public class SQLiteOperationsHelper
{
    private Context mContext;
    private DatabaseHelper mDatabaseHelper;

    public SQLiteOperationsHelper(Context paramContext)
    {
        this.mContext = paramContext;
        this.mDatabaseHelper = DatabaseHelper.getInstance(this.mContext);
        this.mDatabaseHelper.getReadableDatabase().close();
    }

    private String getStringFromColumn(Cursor paramCursor, String paramString)
    {
        return paramCursor.getString(paramCursor.getColumnIndex(paramString));
    }

    public void close(SQLiteDatabase paramSQLiteDatabase)
    {
        paramSQLiteDatabase.close();
    }

    public String getIconStringFromMac(String paramString)
    {
        String str = "device";
        SQLiteDatabase localSQLiteDatabase = open();
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT type FROM macvendor WHERE mac = \"" + paramString + "\" COLLATE NOCASE", null);
        if (localCursor.moveToFirst()) {
            str = getStringFromColumn(localCursor, "type");
        }
        localCursor.close();
        close(localSQLiteDatabase);
        return str;
    }

    public String getVendorFromMac(String paramString)
    {
        String str = this.mContext.getString(R.string.unknown_vendor);
        SQLiteDatabase localSQLiteDatabase = open();
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT vendor FROM macvendor WHERE mac = \"" + paramString + "\" COLLATE NOCASE", null);
        if (localCursor.moveToFirst()) {
            str = getStringFromColumn(localCursor, "vendor");
        }
        localCursor.close();
        close(localSQLiteDatabase);
        return str;
    }

    public SQLiteDatabase open()
    {
        return this.mDatabaseHelper.getReadableDatabase();
    }
}