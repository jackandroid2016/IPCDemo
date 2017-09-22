package com.zl.ipctest;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 名称:
 * 功能:
 * 创建人: ZhuLei
 * 创建日期: 2017/9/21 14:54
 */
public class ClientLastly implements Runnable {

    private final String TAG = this.getClass().getSimpleName();
    private static final String NAME = "com.repackaging.localsocket";
    private int timeout = 30000;
    Socket client;
    PrintWriter os;
    BufferedReader is;

    Handler handler;

    ClientLastly(Handler handler){
        this.handler=handler;
    }

    //发数据
    public void send(String data){
        Log.i(TAG, "Client=======data=========");
        if (os != null) {
            os.println(data);
            os.flush();
        }
    }

    //接收据
    @Override
    public void run() {
        try {
            //连接服务器
//            client=new Socket("192.168.191.1", 8080);
//            client=new Socket("10.18.51.14", 8080);
            client = new Socket("127.0.0.1", 8080);
            Log.i(TAG, "Client=======连接服务器成功=========");
            client.setSoTimeout(timeout);
            os = new PrintWriter(client.getOutputStream());
            is = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String result="";
        while(true){
            try {
                result = is.readLine();
                Log.i(TAG, "客户端接到的数据为："+result);
                //将数据带回acitvity显示
                Message msg=handler.obtainMessage();
                msg.arg1=0x12;
                msg.obj=result;
                handler.sendMessage(msg);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void close(){
        try {
            if (os!=null) {
                os.close();
            }
            if (is!=null) {
                is.close();
            }
            if(client!=null){
                client.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
