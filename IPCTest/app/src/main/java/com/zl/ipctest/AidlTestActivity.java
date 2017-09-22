package com.zl.ipctest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 名称:
 * 功能:
 * 创建人: ZhuLei
 * 创建日期: 2017/9/14 14:12
 */
public class AidlTestActivity extends AppCompatActivity implements View.OnClickListener {

    TestAidl mTestAidl;
    private Button btn_aidl;
    private TextView tv_aidl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        initView();

        //绑定服务
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.zl.ipcservicetest", "com.zl.ipcservicetest.MyService"));
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    /**
     * 点击“AIDL”按钮事件
     */
    public void add() {
        try {
            int res = mTestAidl.add(1, 2);
            Log.i("客户端", "从服务端调用成功的结果：" + res);
            tv_aidl.setText("从服务端调用成功的结果：" + res);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务回调方法
     */
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mTestAidl = TestAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mTestAidl = null;
        }
    };

    private void initView() {
        btn_aidl = (Button) findViewById(R.id.btn_aidl);
        tv_aidl = (TextView) findViewById(R.id.tv_aidl);

        btn_aidl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_aidl:
                add();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        //解绑服务，回收资源
        unbindService(conn);
        super.onDestroy();
    }
}
