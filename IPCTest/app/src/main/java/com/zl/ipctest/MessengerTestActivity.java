package com.zl.ipctest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 名称:
 * 功能:
 * 创建人: ZhuLei
 * 创建日期: 2017/9/14 14:12
 */
public class MessengerTestActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_messenger;
    private TextView tv_messenger;

    //标识
    public static final int GET_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        initView();
    }

    private void initView() {
        btn_messenger = (Button) findViewById(R.id.btn_messenger);
        tv_messenger = (TextView) findViewById(R.id.tv_messenger);

        btn_messenger.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_messenger:
                //绑定服务
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.zl.ipcservicetest", "com.zl.ipcservicetest.MessagerService"));
                bindService(intent, conn, BIND_AUTO_CREATE);
                break;
        }
    }

    /**
     * 服务回调方法
     */
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            String data = "这是客户端发过去的消息";
            //获得消息者
            Messenger messenger = new Messenger(service);
            //创建空消息
            Message message = Message.obtain(null, GET_RESULT);
            Bundle bundle = new Bundle();
            bundle.putString("data", data);
            //往消息中存数据
            message.setData(bundle);
            //消息者发送消息
            try {
                messenger.send(message);
                tv_messenger.setText("客户端:" + data);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
