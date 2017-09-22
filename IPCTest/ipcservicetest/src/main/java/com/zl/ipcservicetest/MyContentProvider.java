package com.zl.ipcservicetest;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 名称: MyContentProvider
 * 功能: 除了onCreate()是运行在主线程中，其他的五个方法都是由外界回调并且运行到Binder线程池中。
 * 创建人: ZhuLei
 * 创建日期: 2017/9/18 16:15
 */
public class MyContentProvider extends ContentProvider {

    private final String TAG = this.getClass().getSimpleName();

    private SQLiteDatabase db;
    private DBOpenHelper dbOpenHelper;

    private ContentResolver contentResolver;
    private static final int MULTIPLE_PEOPLE = 1;
    private static final int SINGLE_PEOPLE = 2;

    private static final UriMatcher uriMatcher ;
    public static final String AUTHORITY = "com.zl.ipcservicetest.mycontentprovider"; //这个是唯一标识和XML里面authrities一样。
    public static final String PATH_SINGLE = "member/#"; // #匹配所有数字，*匹配所有字符
    public static final String PATH_MULTIPLE = "member";
    public static final String CONTENT_URI_STRING = "content://" + AUTHORITY + "/" + PATH_MULTIPLE;
    public static final String CONTENT_URI_STRING_SINGLE = "content://" + AUTHORITY + "/" + PATH_SINGLE;
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING); //member表格的URI
    public static final Uri CONTENT_URI_SINGLE = Uri.parse(CONTENT_URI_STRING_SINGLE); //member表格的URI

    public static final String MIME_DIR_PREFIX = "vnd.android.cursor.dir";  // dir代表多行数据
    public static final String MIME_ITEM_PREFIX = "vnd.android.cursor.item";  // item代表单行数据
    public static final String MIME_ITEM = "vnd.example.member";

    public static final String MIME_TYPE_SINGLE = MIME_ITEM_PREFIX + "/" + MIME_ITEM ;
    public static final String MIME_TYPE_MULTIPLE = MIME_DIR_PREFIX + "/" + MIME_ITEM ;

    /**
     * ContentProvider通过URI来区分外界要访问的数据集合，所以我们要对特定的表定义特定的URI和URI_CODE
     */
    static {

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);  //创建UriMatcher
        uriMatcher.addURI(AUTHORITY, PATH_MULTIPLE, MULTIPLE_PEOPLE);  //将URI和code关联到一起
        uriMatcher.addURI(AUTHORITY, PATH_SINGLE, SINGLE_PEOPLE);  //将URI和code关联到一起
    }

    /**
     * 一般用来初始化底层数据集和建立数据连接等工作
     * @return
     */
    @Override
    public boolean onCreate() {
        Log.i(TAG, "MyContentProvider onCreate");
        Context context = getContext();
        contentResolver = context.getContentResolver();
        dbOpenHelper = DBOpenHelper.getHelper(context);
        db = dbOpenHelper.getWritableDatabase();
        if(db == null){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 用来返回指定URI的MIME数据类型，若URI是单条数据，则返回的MIME数据类型以vnd.android.cursor.item开头；
     * 若URI是多条数据，则返回的MIME数据类型以vnd.android.cursor.dir/开头。
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.i(TAG, "MyContentProvider getType");
        int code = uriMatcher.match(uri);
        String type = null;
        if (code == MULTIPLE_PEOPLE) {
            type = MIME_TYPE_MULTIPLE;// dir代表多行数据
        } else if (code == SINGLE_PEOPLE) {
            type = MIME_TYPE_SINGLE;// item单行
        }
        return type;
    }

    /**
     * 对数据进行增加操作，该方法会导致数据库里面数据发生改变，如有需要
     * 可通过ContentResolver的notifychange方法通知外界当前的ContentProvider已经发生改变。
     * @param uri
     * @param values  代表一行记录的数据
     * @return  代表访问新添加数据的uri
     */
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i(TAG, "MyContentProvider insert");
        int code = uriMatcher.match(uri);
        if (code != MULTIPLE_PEOPLE && code != SINGLE_PEOPLE) {
            throw new RuntimeException("Uri地址错误");
        }
        // insert()参数说明：
        // table：代表想插入数据的表名。
        // nullColumnHack：代表强行插入null值的数据列的列名。
        // values：代表一行记录的数据。
        long row = db.insert(PATH_MULTIPLE, null, values);
        if (row > 0) {
            contentResolver.notifyChange(uri, null);
        }
        Uri newUri = ContentUris.withAppendedId(uri, row);
        return newUri;
    }

    /**
     * 对数据进行删除操作，该方法会导致数据库里面数据发生改变，如有需要
     * 可通过ContentResolver的notifychange方法通知外界当前的ContentProvider已经发生改变。
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return  删除条目的个数
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i(TAG, "MyContentProvider delete");
        int code = uriMatcher.match(uri);
        if (code != MULTIPLE_PEOPLE && code != SINGLE_PEOPLE) {
            throw new RuntimeException("Uri地址错误");
        }
        int row = 0;
//        if (code == MULTIPLE_PEOPLE) {
//            throw new RuntimeException("不能删除所有数据");
//        } else if (code == SINGLE_PEOPLE) {
//            long id = ContentUris.parseId(uri);
//            row = db.delete(PATH_MULTIPLE, "id=?", new String[] { id + "" });
            // delete()参数说明：
            // table：代表想要更新数据的表名。
            // whereClause：满足该whereClause子句的记录将会被删除。
            // whereArgs：用于为whereArgs子句传入参数。
            row = db.delete(PATH_MULTIPLE, selection, selectionArgs);
            if (row > 0) {
                contentResolver.notifyChange(uri, null);
            }
//        }
        return row;
    }

    /**
     * 对数据进行更新操作，该方法会导致数据库里面数据发生改变，如有需要
     * 可通过ContentResolver的notifychange方法通知外界当前的ContentProvider已经发生改变。
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return  改变成功的个数
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.i(TAG, "MyContentProvider update");
        int code = uriMatcher.match(uri);
        if (code != MULTIPLE_PEOPLE && code != SINGLE_PEOPLE) {
            throw new RuntimeException("Uri地址错误");
        }
        int row = 0;
//        if (code == MULTIPLE_PEOPLE) {
//            throw new RuntimeException("不能更新所有数据");
//        } else if (code == SINGLE_PEOPLE) {
//            long id = ContentUris.parseId(uri);
//            row = db.update("t_apps", values, "id=?", new String[] { id + "" });
            // update()参数说明：
            // table：代表想要更新数据的表名。
            // values：代表想要更新的数据。
            // whereClause：满足该whereClause子句的记录将会被更新。
            // whereArgs：用于为whereArgs子句传递参数。
            row = db.update(PATH_MULTIPLE, values, selection, selectionArgs);
            if (row > 0) {
                contentResolver.notifyChange(uri, null);
            }
//        }
        return row;
    }

    /**
     * 对数据进行查询操作
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i(TAG, "MyContentProvider query");
        int code = uriMatcher.match(uri);
        if (code != MULTIPLE_PEOPLE && code != SINGLE_PEOPLE) {
            throw new RuntimeException("Uri地址错误");
        }
        Cursor cursor = null;
//        if (code == MULTIPLE_PEOPLE) {
            // query()参数说明：
            // distinct：指定是否去除重复记录。 默认是false
            // table：执行查询数据的表名。
            // columns：要查询出来的列名。
            // selection：查询条件子句。
            // selectionArgs：用于为selection子句中占位符传入参数值，值在数组中的位置与占位符在语句中的位置必须一致，否则就会有异常。
            // groupBy：用于控制分组。
            // having：用于对分组进行过滤。
            // orderBy：用于对记录进行排序。
            // limit：用于进行分页。  格式："5,10"
//            cursor = db.query(PATH_MULTIPLE, new String[] { "id", "name", "package_name", "activity_name", "icon", "cate_id" }, selection,
//                    selectionArgs, null, null, sortOrder);
            cursor = db.query(PATH_MULTIPLE, projection, selection, selectionArgs, null, null, sortOrder);
//        } else if (code == SINGLE_PEOPLE) {
//            long id = ContentUris.parseId(uri);// 从uri中取出id
//            cursor = db.query(PATH_MULTIPLE, new String[] { "id", "name", "package_name", "acitivty_name", "icon", "cate_id" }, "id=?",
//                    new String[] { String.valueOf(id) }, null, null, sortOrder);
//        }
        return cursor;
    }

    /**
     * 相当于远程调用
     * @param method
     * @param arg
     * @param extras
     * @return
     */
    @Nullable
    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        Log.i(TAG, "MyContentProvider call");
        return super.call(method, arg, extras);
    }
}
