package com.zl.ipcservicetest;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

/**
 * 名称:
 * 功能:
 * 创建人: ZhuLei
 * 创建日期: 2017/9/14 17:09
 */
public class MessagerService extends Service {

    //标识
    public static final int GET_RESULT = 1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    private final Messenger mMessenger = new Messenger(new Handler() {
        @SuppressLint("ShowToast")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_RESULT:
                    Log.i("服务端", msg.getData().getString("data"));
                    MessageEvent messageEvent = new MessageEvent("messager", msg.getData().getString("data"));
                    EventBus.getDefault().post(messageEvent);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    });
}
