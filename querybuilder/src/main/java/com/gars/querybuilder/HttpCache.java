package com.gars.querybuilder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 */
public class HttpCache extends DbBase{

    public static SQLiteDatabase DB;
    public static final String DATABASE_NAME = "httpcache";
    public static final int DATABASE_VERSION = 3;

    public void open_db() {
        if (!isOpen())
            DB = this.getWritableDatabase();
    }

    public boolean isOpen() {
        return DB != null && DB.isOpen();
    }

    public SQLiteDatabase getDb() {
        return DB;
    }

    public HttpCache(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        open_db();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DbCache.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DbCache.update(db, oldVersion, newVersion);
    }
}
