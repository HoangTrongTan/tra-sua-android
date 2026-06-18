package com.example.trasua_android.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.trasua_android.TruyVan.thucthi;

public class db_sqlite extends SQLiteOpenHelper {
    public static String TABLE_Taikhoan = "Taikhoan";
    public static String COL_TAIKHOAN_tendangnhap = "tendangnhap";
    public static String COL_TAIKHOAN_matkhau = "matkhau";
    public static String COL_TAIKHOAN_hinhanh = "hinhanh";

    public static String TABLE_MONAN = "MONAN";
    public static String COL_MONAN_ten_mon = "ten_mon";
    public static String COL_MONAN_hinh_anh = "hinh_anh";

    public static String TABLE_BANAN = "BANAN";
    public static String COL_BANAN_ten_ban = "ten_ban";
    public static String COL_BANAN_trangthai = "trangthai";

    private static final String DB_NAME = "BanHang";
    private static final int DB_VERSION = 1;

    private static db_sqlite instance = null;

    public static synchronized db_sqlite getInstance(Context context){
        if(instance == null){
            instance = new db_sqlite(context);
        }
        return instance;
    }

    public db_sqlite(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(thucthi.QUERY_CREATE_TABLE_TAIKHOAN);
        db.execSQL(thucthi.QUERY_CREATE_TABLE_MONAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
