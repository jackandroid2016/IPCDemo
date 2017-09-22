package com.zl.ipcservicetest;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * 名称:
 * 功能:
 * 创建人: ZhuLei
 * 创建日期: 2017/9/14 14:12
 */
public class FileTestActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_file;
    private TextView tv_file;

    private final String CHAPTER_2_PATH = Environment.getExternalStorageDirectory().getPath() + "/zl/ipctestuser/";
    private final String CACHE_FILE_PATH = CHAPTER_2_PATH + "usercache";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        initView();
    }

    private void initView() {
        btn_file = (Button) findViewById(R.id.btn_file);
        tv_file = (TextView) findViewById(R.id.tv_file);

        btn_file.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_file:
                recoverFromFile();
//                User user = null;
//                ObjectInputStream objectInputStream = null;
//                try {
//                    objectInputStream = new ObjectInputStream(new FileInputStream(SDPATH + filepath));
//                    Object readObject = objectInputStream.readObject();
//                    user = (User) readObject;
//                    tv_file.setText(user.toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }

                break;
        }
    }

    private void recoverFromFile(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = null;
                final File cachedFile = new File(CACHE_FILE_PATH);
                if(cachedFile.exists()){
                    ObjectInputStream objectInputStream = null;
                    try{
                        objectInputStream = new ObjectInputStream(new FileInputStream(cachedFile));
//                        user = (User)objectInputStream.readObject();
                        final String s = (String) objectInputStream.readObject();
                        final User finalUser = user;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_file.setText("路径:" + cachedFile.getPath() + "======recover user:" + s);
//                                tv_file.setText("路径:" + cachedFile.getPath() + "======recover user:" + finalUser.toString());
                            }
                        });
                    }catch (IOException e) {
                        e.printStackTrace();
                    }catch (ClassNotFoundException e){
                        e.printStackTrace();
                    }finally {
                        close(objectInputStream);
                    }
                }
            }
        }).start();
    }

    public void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
