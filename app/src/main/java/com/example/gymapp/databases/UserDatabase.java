package com.example.gymapp.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.gymapp.model.User;

public class UserDatabase {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public UserDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        if (database == null || !database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    // Thêm người dùng
    public long themNguoiDung(String username, String fullname, String password, String role, String email, String birthday, String gender, String avatar) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.USER_COLUMN_USERNAME, username);
        values.put(DatabaseHelper.USER_COLUMN_FULLNAME, fullname);
        values.put(DatabaseHelper.USER_COLUMN_PASSWORD, password);
        values.put(DatabaseHelper.USER_COLUMN_ROLE, role);
        values.put(DatabaseHelper.USER_COLUMN_EMAIL, email);
        values.put(DatabaseHelper.USER_COLUMN_BIRTHDAY, birthday);
        values.put(DatabaseHelper.USER_COLUMN_GENDER, gender);
        values.put(DatabaseHelper.USER_COLUMN_AVATAR, avatar);

        long id = db.insert(DatabaseHelper.USER_TABLE_NAME, null, values);
        db.close();
        return id;
    }

    // Xóa người dùng theo ID
    public int xoaNguoiDung(int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsAffected = db.delete(DatabaseHelper.USER_TABLE_NAME, DatabaseHelper.USER_COLUMN_ID + " = ?", new String[]{String.valueOf(userId)});
        db.close();
        return rowsAffected;
    }

    // Sửa thông tin người dùng
    public int suaNguoiDung(int userId, String username, String fullname, String password, String role, String email, String birthday, String gender, String avatar) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.USER_COLUMN_USERNAME, username);
        values.put(DatabaseHelper.USER_COLUMN_FULLNAME, fullname);
        values.put(DatabaseHelper.USER_COLUMN_PASSWORD, password);
        values.put(DatabaseHelper.USER_COLUMN_ROLE, role);
        values.put(DatabaseHelper.USER_COLUMN_EMAIL, email);
        values.put(DatabaseHelper.USER_COLUMN_BIRTHDAY, birthday);
        values.put(DatabaseHelper.USER_COLUMN_GENDER, gender);
        values.put(DatabaseHelper.USER_COLUMN_AVATAR, avatar);

        int rowsAffected = db.update(DatabaseHelper.USER_TABLE_NAME, values, DatabaseHelper.USER_COLUMN_ID + " = ?", new String[]{String.valueOf(userId)});
        db.close();
        return rowsAffected;
    }

    // Truy vấn tất cả người dùng
    public List<User> layToanBoNguoiDung() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                DatabaseHelper.USER_COLUMN_ID,
                DatabaseHelper.USER_COLUMN_USERNAME,
                DatabaseHelper.USER_COLUMN_FULLNAME,
                DatabaseHelper.USER_COLUMN_PASSWORD,
                DatabaseHelper.USER_COLUMN_ROLE,
                DatabaseHelper.USER_COLUMN_EMAIL,
                DatabaseHelper.USER_COLUMN_BIRTHDAY,
                DatabaseHelper.USER_COLUMN_GENDER,
                DatabaseHelper.USER_COLUMN_AVATAR
        };

        Cursor cursor = db.query(DatabaseHelper.USER_TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_ID));
                String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_USERNAME));
                String fullname = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_FULLNAME));
                String password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_PASSWORD));
                String role = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_ROLE));
                String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_EMAIL));
                String birthdayStr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_BIRTHDAY));

                Date birthday = null;
                try {
                    birthday = dateFormat.parse(birthdayStr);
                } catch (ParseException e) {
                    e.printStackTrace(); // Xử lý lỗi nếu ngày không hợp lệ
                }

                String gender = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_GENDER));
                String avatar = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_AVATAR));

                User user = new User(id, username, fullname, password, role, email, birthday, gender, avatar);
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    public boolean capNhatNguoiDung(int userId, String username, String fullname, String role, String email, String birthday, String gender) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();

            // Create a ContentValues object to hold the updated data
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.USER_COLUMN_USERNAME, username);
            values.put(DatabaseHelper.USER_COLUMN_FULLNAME, fullname);
            values.put(DatabaseHelper.USER_COLUMN_ROLE, role);
            values.put(DatabaseHelper.USER_COLUMN_EMAIL, email);
            values.put(DatabaseHelper.USER_COLUMN_BIRTHDAY, birthday);
            values.put(DatabaseHelper.USER_COLUMN_GENDER, gender);

            // Update the user in the database
            int rowsAffected = db.update(
                    DatabaseHelper.USER_TABLE_NAME,
                    values,
                    DatabaseHelper.USER_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(userId)}
            );

            // Check if the update was successful
            return rowsAffected > 0;
        } catch (Exception e) {
            Log.e("UserDatabase", "Error updating user: " + e.getMessage());
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public String getUserFullNameById(int userId) {
        String fullname = null;
        Cursor cursor = null;
        try {
            open();
            String query = "SELECT " + DatabaseHelper.USER_COLUMN_FULLNAME +
                    " FROM " + DatabaseHelper.USER_TABLE_NAME +
                    " WHERE " + DatabaseHelper.USER_COLUMN_ID + " = ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(userId)});

            if (cursor.moveToFirst()) {
                fullname = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_FULLNAME));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            close();
        }
        return fullname;
    }

}
