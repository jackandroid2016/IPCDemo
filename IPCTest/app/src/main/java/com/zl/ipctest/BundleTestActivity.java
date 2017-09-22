package com.zl.ipctest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
public class BundleTestActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_bundle;
    private TextView tv_bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle);
        initView();
    }

    private void initView() {
        btn_bundle = (Button) findViewById(R.id.btn_bundle);
        tv_bundle = (TextView) findViewById(R.id.tv_bundle);

        btn_bundle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bundle:
                sendBundleInfo();
                break;
        }
    }

    /**
     * 使用bundle发送数据
     */
    private void sendBundleInfo() {
        //  需要使用Intent类的第2个参数指定Uri
        Intent intent = new  Intent("com.zl.ipcservicetest.MYACTION", Uri.parse("test://BundleTestActivity.class"));
        //  设置value属性值
        Bundle bundle = new Bundle();
        bundle.putString("ipc", "我是客户端");
        intent.putExtras(bundle);
        //  调用ActionActivity中的Main
        startActivity(intent);
    }
}
