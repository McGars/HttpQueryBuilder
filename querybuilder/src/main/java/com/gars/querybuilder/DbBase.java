package com.gars.querybuilder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Феофилактов on 27.03.2015.
 */
public class DbBase extends SQLiteOpenHelper {

    Context c;
    public DbBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        c = context;
    }

    public Context getContext() {
        return c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static String getColumsNames(SQLiteDatabase db, String table) {
        String names = "";
        Cursor ti = db.rawQuery("PRAGMA table_info(" + table + ")", null);
        if (ti.moveToFirst()) {
            do {
                names += " " + ti.getString(1);
            } while (ti.moveToNext());
        }
        ti.close();
        return names;
    }

    public static String getTableNames(SQLiteDatabase db) {
        String result = "";
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT name FROM sqlite_master ");
            sb.append("WHERE type IN ('table','view') AND name NOT LIKE 'sqlite_%' ");
            sb.append("UNION ALL ");
            sb.append("SELECT name FROM sqlite_temp_master ");
            sb.append("WHERE type IN ('table','view') ");
            sb.append("ORDER BY 1");

            Cursor c = db.rawQuery(sb.toString(), null);
            c.moveToFirst();

            while (c.moveToNext()) {
                result += " " + c.getString(c.getColumnIndex("name"));
            }
            c.close();
        } catch (SQLiteException e) {
        }
        return result;
    }
}
