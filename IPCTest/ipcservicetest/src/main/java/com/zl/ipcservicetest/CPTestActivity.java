package com.zl.ipcservicetest;

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
    private Button btn_cp;
    private Button btn_query;
    private Button btn_update;
    private Button btn_delete;
    private TextView tv_cp;
    private int id = 0;
    private int i = 0;

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
                values.put(Member.KEY_ID, (id++));
                values.put(Member.KEY_NAME, "ddd" + (i++));
                values.put(Member.KEY_AGE, "25");
                values.put(Member.KEY_HEIGHT, "175");
                Uri uri = getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
                sb1.append("插入的URi==" + uri.toString() + "\n");
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
                Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI, null, null, null, null);
                // 查询指定数据
//                Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI, new String[] { "id", "name", "age", "height"}, "id>?", new String[]{"3"}, "id DESC");
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
//                int row = getContentResolver().delete(MyContentProvider.CONTENT_URI, null, null);
//                if (row > 0) {
//                    id = 0;
//                    i = 0;
//                }
                // 删除指定数据
                int row = getContentResolver().delete(MyContentProvider.CONTENT_URI, "id=?", new String[]{"2"});
                tv_cp.setText("删除了" + row + "行");
                break;
            case R.id.btn_update:
                ContentValues values1 = new ContentValues();
                values1.put(Member.KEY_NAME, "updete");
                values1.put(Member.KEY_AGE, "26");
                values1.put(Member.KEY_HEIGHT, "190");
                // 更新指定行
                int row1 = getContentResolver().update(MyContentProvider.CONTENT_URI, values1, "id=?", new String[]{"3"});
                // 更新整张表
//                int row1 = getContentResolver().update(MyContentProvider.CONTENT_URI, values1, null, null);
                tv_cp.setText("更新了" + row1 + "行");
                break;
            default:
                break;
        }
    }
}
