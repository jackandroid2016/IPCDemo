package com.zl.ipcservicetest;

import android.os.Bundle;
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
                //  获得其他应用程序传递过来的数据
                if (getIntent().getData() != null) {
                    //  获得Host，也就是test://后面的内容
                    String host = getIntent().getData().getHost();
                    Log.i("host", host);
                    Bundle bundle = getIntent().getExtras();
                    //  其他的应用程序会传递过来一个value值，在该应用程序中需要获得这个值
                    String value = bundle.getString("ipc");
                    //  将Host和Value组合在一下显示在TextText组件中
                    tv_bundle.setText(host + ":" + value);
                }
                break;
        }
    }
}
