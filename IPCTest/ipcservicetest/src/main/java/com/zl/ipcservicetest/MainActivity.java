package com.zl.ipcservicetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 服务端
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_bundle;
    private Button btn_file;
    private Button btn_aidl;
    private Button btn_cp;
    private Button btn_messenger;
    private Button btn_socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_bundle = (Button) findViewById(R.id.btn_bundle);
        btn_file = (Button) findViewById(R.id.btn_file);
        btn_aidl = (Button) findViewById(R.id.btn_aidl);
        btn_cp = (Button) findViewById(R.id.btn_cp);
        btn_messenger = (Button) findViewById(R.id.btn_messenger);
        btn_socket = (Button) findViewById(R.id.btn_socket);

        btn_bundle.setOnClickListener(this);
        btn_file.setOnClickListener(this);
        btn_aidl.setOnClickListener(this);
        btn_cp.setOnClickListener(this);
        btn_messenger.setOnClickListener(this);
        btn_socket.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bundle:
                startActivity(new Intent(MainActivity.this, BundleTestActivity.class));
                break;
            case R.id.btn_file:
                startActivity(new Intent(MainActivity.this, FileTestActivity.class));
                break;
            case R.id.btn_aidl:
                startActivity(new Intent(MainActivity.this, AidlTestActivity.class));
                break;
            case R.id.btn_cp:
                startActivity(new Intent(MainActivity.this, CPTestActivity.class));
                break;
            case R.id.btn_messenger:
                startActivity(new Intent(MainActivity.this, MessengerTestActivity.class));
                break;
            case R.id.btn_socket:
                startActivity(new Intent(MainActivity.this, SocketTestActivity.class));
                break;
        }
    }
}
