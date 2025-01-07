package com.example.gymapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gymapp.databases.DatabaseHelper;

public class DatabaseVersionChecker {

    public static int getDatabaseVersion(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = null;
        int version = -1;
        try {
            db = dbHelper.getReadableDatabase();
            version = db.getVersion(); // Lấy phiên bản cơ sở dữ liệu
        } finally {
            if (db != null) {
                db.close(); // Đảm bảo đóng cơ sở dữ liệu sau khi sử dụng
            }
        }
        return version;
    }
}

