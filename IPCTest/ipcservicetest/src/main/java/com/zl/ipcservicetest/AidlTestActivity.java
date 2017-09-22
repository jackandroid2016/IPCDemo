package com.zl.ipcservicetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 名称:
 * 功能:
 * 创建人: ZhuLei
 * 创建日期: 2017/9/14 14:12
 */
public class AidlTestActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_aidl;
    private TextView tv_aidl;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_aidl);
        initView();
    }

    private void initView() {
        btn_aidl = (Button) findViewById(R.id.btn_aidl);
        tv_aidl = (TextView) findViewById(R.id.tv_aidl);

        btn_aidl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_aidl:
                mIntent = new Intent(this, MyService.class);
                startService(mIntent);
                tv_aidl.setText("服务端已开启");
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        if (null != event) {
            switch (event.getTitle()) {
                case "aidl":
                    String content = (String) event.getContent();
                    tv_aidl.setText("服务端：" + content);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
