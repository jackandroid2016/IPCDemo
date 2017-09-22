package com.zl.ipctest;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
                persistToFile();
//                User user = new User(1, "zl");
//                //新建一个File
//                File file = new File(SDPATH + filepath);
//                if (!file.exists()) {
//                    try {
//                        file.createNewFile();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                ObjectOutputStream objectOutputStream = null;
//                try {
//                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(file.getPath()));
//                    objectOutputStream.writeObject(user);
//                    tv_file.setText("写入成功" + user.toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                break;
        }
    }

    private void persistToFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final User user = new User(1, "zl");
                final String s = "我是客户端";
                File dir = new File(CHAPTER_2_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                final File cachedFile = new File(CACHE_FILE_PATH);
                ObjectOutputStream objectOutputStream = null;
                try {
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(cachedFile));
                    objectOutputStream.writeObject(s);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            tv_file.setText("路径:" + cachedFile.getPath() + "=======persist user:" + user);
                            tv_file.setText("路径:" + cachedFile.getPath() + "=======persist user:" + s);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    close(objectOutputStream);
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
