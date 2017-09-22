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
public class MessengerTestActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_messenger;
    private TextView tv_messenger;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
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
                mIntent = new Intent(this, MessagerService.class);
                startService(mIntent);
                tv_messenger.setText("服务端开启成功");
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        if (null != event) {
            switch (event.getTitle()) {
                case "messager":
                    String content = (String) event.getContent();
                    tv_messenger.setText("服务端：" + content);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        stopService(mIntent);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
