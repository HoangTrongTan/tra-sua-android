package com.example.trasua_android.TruyVan;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.ContentInfo;

import com.example.trasua_android.Databases.db_sqlite;

public class thucthi {
    public static final String QUERY_CREATE_TABLE_TAIKHOAN = "create table Taikhoan (" +
            "_id integer primary key autoincrement," +
            "tendangnhap varchar(100),"+
            "matkhau nvarchar(150), "+
            "hinhanh BLOB )"
            ;
    public static final String QUERY_CREATE_TABLE_MONAN = "create table MONAN (" +
            "_id integer primary key autoincrement ,"+
            " ten_mon nvarchar(150),"+
            " hinh_anh BLOB )"
            ;
    public static final String QUERY_CREATE_TABLE_BANAN = "create table BANAN (" +
            "_id integer primary key autoincrement ,"+
            " ten_ban nvarchar(150),"+
            " trangthai integer )"
            ;
    public static Cursor check_login(String ten,String pass){
        String sql = "SELECT * FROM "+ db_sqlite.TABLE_Taikhoan;
        SQLiteDatabase sqLiteDatabase = db_sqlite.getInstance(null).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        return cursor;
    }
    public static long insert_Taikhoan(String ten,String pass,byte[] hinhanh){
        SQLiteDatabase sqLiteDatabase = db_sqlite.getInstance(null).getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(db_sqlite.COL_TAIKHOAN_tendangnhap,ten);
        values.put(db_sqlite.COL_TAIKHOAN_matkhau,pass);
        values.put(db_sqlite.COL_TAIKHOAN_hinhanh,hinhanh);
        return sqLiteDatabase.insert(db_sqlite.TABLE_Taikhoan,null,values);
    }
}
