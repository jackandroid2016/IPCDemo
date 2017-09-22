package com.zl.ipcservicetest;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 名称:
 * 功能:
 * 创建人: ZhuLei
 * 创建日期: 2017/9/18 17:02
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "member_provider_db";
    public static final String MEMBER_TABLE_NAME = "member";
    private static final int DB_VERSION = 1;

    private static DBOpenHelper mInstance;

    public static final String CREATE_TABLE_MEMBER = "create table " + MEMBER_TABLE_NAME +
            "(" + Member.KEY_ID + " integer primary key autoincrement, " +
            Member.KEY_NAME + " text not null, " + Member.KEY_AGE + " integer, " +
            Member.KEY_HEIGHT + " float);";

    public static synchronized DBOpenHelper getHelper(Context context) {
        if (mInstance == null) {
            synchronized (DBOpenHelper.class) {
                if (mInstance == null) {
                    mInstance = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
                }
            }
        }
        return mInstance;
    }

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MEMBER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MEMBER_TABLE_NAME);
        onCreate(db);
    }
}
