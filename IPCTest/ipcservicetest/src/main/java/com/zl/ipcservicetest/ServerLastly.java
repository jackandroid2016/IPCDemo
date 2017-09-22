package com.zl.ipcservicetest;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 名称:
 * 功能:
 * 创建人: ZhuLei
 * 创建日期: 2017/9/21 14:21
 */
public class ServerLastly implements Runnable{

    private final String TAG = this.getClass().getSimpleName();
//    ServerSocket是等待客户端的请求，一旦获得一个连接请求，就创建一个Socket实例来与客户端进行通信。
    ServerSocket server;
//    socket可以使一个应用从网络中读取和写入数据，不同计算机上的两个应用可以通过连接发送和接受字节流，当发送消息时，你需要知道对方的ip和端口
    Socket client;
    PrintWriter os;
    BufferedReader is;

    Handler handler;

    /**
     * 此处不将连接代码写在构造方法中的原因：
     * 我在activity的onCreate()中创建示例，如果将连接代码 写在构造方法中，服务端会一直等待客户端连接，界面没有去描绘，会一直出现白屏。
     * 直到客户端连接上了，界面才会描绘出来。原因是构造方法阻塞了主线程，要另开一个线程。在这里我将它写在了run()中。
     */
    ServerLastly(Handler handler){
        this.handler = handler;
    }

    //发数据
    public void send(String data){
        if (os != null) {
            os.println(data);
            os.flush();
        }
    }

    //接数据
    @Override
    public void run() {
        Log.i(TAG, "Server=======打开服务=========");
        try {
            server = new ServerSocket(8080);
            client = server.accept();
            Log.i(TAG, "Server=======客户端连接成功=========");
            InetAddress inetAddress = client.getInetAddress();
            String ip = inetAddress.getHostAddress();
            Log.i(TAG, "===客户端ID为:"+ip);
            // client.getOutputStream()发送字节流
            os = new PrintWriter(client.getOutputStream());
            // client.getInputStream()接收远程对象发送来的信息
            is = new BufferedReader(new InputStreamReader(client.getInputStream()));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String result = "";
        while(true){
            try {
                result = is.readLine();
                Log.i(TAG, "服务端接到的数据为：" + result);
                //把数据带回activity显示
                Message msg = handler.obtainMessage();
                msg.obj = result;
                msg.arg1 = 0x11;
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
            if (server!=null) {
                server.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
