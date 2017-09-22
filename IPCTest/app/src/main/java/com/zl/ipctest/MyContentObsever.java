package com.zl.ipctest;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

/**
 * 名称:
 * 功能:
 * 创建人: ZhuLei
 * 创建日期: 2017/9/18 16:39
 */
public class MyContentObsever extends ContentObserver {

    private final String TAG = this.getClass().getSimpleName();
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public MyContentObsever(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        // 客户端可以得到数据源发生改变的通知
        Log.i(TAG, "数据源发生了变化！");
        super.onChange(selfChange);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
    }
}
