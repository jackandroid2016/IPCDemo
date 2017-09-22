package com.zl.ipcservicetest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 名称:
 * 功能:
 * 创建人: ZhuLei
 * 创建日期: 2017/9/14 14:12
 */
public class SocketTestActivity extends AppCompatActivity implements View.OnClickListener {

    private ServerLastly server;
    private StringBuffer receiveData = new StringBuffer();
    private Button btn_socket;
    private EditText et_content;
    private TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        initView();

        server = new ServerLastly(handler);
        new Thread(server).start();
    }

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.arg1 == 0x11) {
                receiveData.append((String) msg.obj);
                tv_show.setText(receiveData);
                receiveData.append("\r\n");
            }

            return false;
        }
    });

    @Override
    protected void onDestroy() {
        server.close();
        super.onDestroy();
    }

    private void initView() {
        btn_socket = (Button) findViewById(R.id.btn_socket);
        et_content = (EditText) findViewById(R.id.et_content);
        tv_show = (TextView) findViewById(R.id.tv_show);

        btn_socket.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //发数据
            case R.id.btn_socket:
                server.send(et_content.getText().toString() + "");
                et_content.setText("");
                break;
        }
    }

}
