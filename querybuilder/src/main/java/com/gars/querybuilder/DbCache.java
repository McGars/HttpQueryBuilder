package com.gars.querybuilder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class DbCache extends HttpCache {
    public static final String TABLE = "cache_table";
    public static final String ID = "_id";
    public static final String QUERY_ROW  = "query_row";
    public static final String QUERY_DATA = "query_data";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";

    /**
     * Открытие db
     *
     * @param context
     */
    public DbCache(Context context) {
        super(context);
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
    }

    public static void create(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
                        ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        QUERY_ROW + " TEXT, " +
                        QUERY_DATA + " BLOB, " +
                        CREATED_AT + " INTEGER, " +
                        UPDATED_AT + " INTEGER" +
                        "); "
        );
        createIndex(db);
    }


    public static void update(SQLiteDatabase db, int oldVersion, int newVersion) {
        drop(db);
        create(db);
        createIndex(db);
    }

    private static void createIndex(SQLiteDatabase db) {
        db.execSQL("CREATE INDEX IF NOT EXISTS IDX_QUERY_ROW on " + TABLE + " (" + QUERY_ROW + ")");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Cursor getData(String queryRow){
        clearOldCache();
        StringBuilder str = new StringBuilder("SELECT * FROM ")
                .append(TABLE)
                .append(" WHERE ")
                .append(QUERY_ROW)
                .append("= ? AND ")
                .append(CREATED_AT)
                .append(">=")
                .append(System.currentTimeMillis());
        return DB.rawQuery(str.toString(), new String[]{queryRow});
    }

    void clearOldCache(){
        StringBuilder str = new StringBuilder("DELETE FROM ")
                .append(TABLE)
                .append(" WHERE ")
                .append(CREATED_AT)
                .append("<")
                .append(System.currentTimeMillis());
        DB.execSQL(str.toString());
    }

    public void delete(String queryRow){
        DB.delete(TABLE, QUERY_ROW + " = ?", new String[]{queryRow});
    }

    public void setData(String queryRow, byte[] queryData, long timeCache) {
        delete(queryRow);
        ContentValues cv = new ContentValues();
        cv.put(QUERY_ROW, queryRow);
        cv.put(QUERY_DATA, queryData);
        cv.put(CREATED_AT, System.currentTimeMillis() + timeCache);
        getDb().insert(TABLE, null, cv);
    }

    public void invalidateCache(String prefix) {
        DB.delete(TABLE, QUERY_ROW + " LIKE ? ", new String[]{"%"+prefix+"%"});
    }

    public void invalidateCache(String prefix, ContentValues cv) {

        Set<Map.Entry<String, Object>> s=cv.valueSet();
        Iterator itr = s.iterator();

        if(BaseQuery.isDebug)
            Log.d("DatabaseSync", "ContentValue Length :: " + cv.size());

        CopyOnWriteArrayList<String> params = new CopyOnWriteArrayList<>();

        StringBuilder str = new StringBuilder(QUERY_ROW)
                .append(" LIKE ? ");

        params.add("%"+prefix+"%");

        while(itr.hasNext())
        {
            Map.Entry me = (Map.Entry)itr.next();
            String key = me.getKey().toString();
            Object value =  me.getValue();

            str.append(" AND ")
               .append(QUERY_ROW)
               .append(" LIKE ? ");
            params.add("%"+key+"="+value+"%");

        }

        String[] strParams = new String[params.size()];
        for (int i = 0; i < params.size(); i++) {
            strParams[i] = params.get(i);
        }

        DB.delete(TABLE, str.toString(), strParams);
    }
}