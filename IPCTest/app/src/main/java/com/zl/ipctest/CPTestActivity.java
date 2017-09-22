package com.zl.ipctest;

import android.content.ContentValues;
import android.database.Cursor;
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
public class CPTestActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = this.getClass().getSimpleName();
    private Button btn_cp;
    private Button btn_query;
    private Button btn_update;
    private Button btn_delete;
    private TextView tv_cp;
    private int id = 0;
    private int i = 0;
    private String KEY_ID = "id";
    private String KEY_NAME = "name";
    private String KEY_AGE = "age";
    private String KEY_HEIGHT = "height";

    public static final String AUTHORITY = "com.zl.ipcservicetest.mycontentprovider"; //这个是唯一标识和XML里面authrities一样。
    public static final String PATH_SINGLE = "member/#"; // #匹配所有数字，*匹配所有字符
    public static final String PATH_MULTIPLE = "member";
    public static final String CONTENT_URI_SINGLE = "content://" + AUTHORITY + "/" + PATH_SINGLE;
    public static final String CONTENT_URI_MULTIPLE = "content://" + AUTHORITY + "/" + PATH_MULTIPLE;

    public static final String CONTENT_URI_STRING = "content://" + AUTHORITY + "/" + PATH_MULTIPLE;
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING); //member表格的URI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cptest);
        initView();
    }

    private void initView() {
        btn_cp = (Button) findViewById(R.id.btn_cp);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_update = (Button) findViewById(R.id.btn_update);
        tv_cp = (TextView) findViewById(R.id.tv_cp);

        btn_cp.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cp:
                StringBuffer sb1 = new StringBuffer();
                // 插入数据
                ContentValues values = new ContentValues();
                values.put(KEY_ID, (id++));
                values.put(KEY_NAME, "ddd" + (i++));
                values.put(KEY_AGE, "25");
                values.put(KEY_HEIGHT, "175");
                Uri uri = getContentResolver().insert(CONTENT_URI, values);
                sb1.append("插入的URi==" + uri.toString());
//                Cursor content = getContentResolver().query(uri, null, null, null, null);
//                while (content.moveToNext()) {
//                    String id = content.getString(content.getColumnIndex("id"));
//                    String name = content.getString(content.getColumnIndex("name"));
//                    String age = content.getString(content.getColumnIndex("age"));
//                    String height = content.getString(content.getColumnIndex("height"));
//                    sb1.append("id" + id + ";name==" + name + ";age==" + age + ";height==" + height + "\n");
//                    tv_cp.setText(sb1);
//                }
//                tv_cp.setText("插入的URi==" + uri.toString());
                break;
            case R.id.btn_query:
                StringBuffer sb = new StringBuffer();
                // 查询整张表
                Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
                // 查询指定数据
//                Cursor cursor = getContentResolver().query(CONTENT_URI, new String[] { "id", "name", "age", "height"}, "id>?", new String[]{"3"}, "id DESC");
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String age = cursor.getString(cursor.getColumnIndex("age"));
                    String height = cursor.getString(cursor.getColumnIndex("height"));
                    sb.append("id" + id + ";name==" + name + ";age==" + age + ";height==" + height + "\n");
                    tv_cp.setText(sb);
                }
                break;
            case R.id.btn_delete:
                // 删除整表数据
//                int row = getContentResolver().delete(CONTENT_URI, null, null);
//                if (row > 0) {
//                    id = 0;
//                    i = 0;
//                }
                // 删除指定数据
                int row = getContentResolver().delete(CONTENT_URI, "id=?", new String[]{"2"});
                tv_cp.setText("删除了" + row + "行");
                break;
            case R.id.btn_update:
                ContentValues values1 = new ContentValues();
                values1.put(KEY_NAME, "updete");
                values1.put(KEY_AGE, "26");
                values1.put(KEY_HEIGHT, "190");
                // 更新指定行
                int row1 = getContentResolver().update(CONTENT_URI, values1, "id=?", new String[]{"3"});
                // 更新整张表
//                int row1 = getContentResolver().update(CONTENT_URI, values1, null, null);
                tv_cp.setText("更新了" + row1 + "行");
                break;
            default:
                break;
        }
    }
}
