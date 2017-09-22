package com.zl.ipcservicetest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zl.ipctest.TestAidl;

import org.greenrobot.eventbus.EventBus;

/**
 * 名称:
 * 功能:
 * 创建人: ZhuLei
 * 创建日期: 2017/9/14 16:47
 */
public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myS;
    }

    private IBinder myS = new TestAidl.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.i("服务端", "从客户端发来的AIDL请求:num1->" + num1 + "::num2->" + num2);
            MessageEvent messageEvent = new MessageEvent("aidl", "从客户端发来的AIDL请求:num1->" + num1 + "::num2->" + num2);
            EventBus.getDefault().post(messageEvent);
            return num1 + num2;
        }
    };
}
