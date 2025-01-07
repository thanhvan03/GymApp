package com.example.gymapp.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gymapp.model.Category;
import java.util.ArrayList;
import java.util.List;

public class CategoryDatabase {

    private DatabaseHelper dbHelper;
    public CategoryDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                DatabaseHelper.CATEGORY_COLUMN_ID,
                DatabaseHelper.CATEGORY_COLUMN_NAME,
        };

        Cursor cursor = db.query(DatabaseHelper.CATEGORY_TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.CATEGORY_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CATEGORY_COLUMN_NAME));

                Category category = new Category(id, name);
                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categoryList;
    }

    public int deleteCategory(int categoryID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Xóa hàng dựa trên ID
        int rowsAffected = db.delete(DatabaseHelper.CATEGORY_TABLE_NAME,
                DatabaseHelper.CATEGORY_COLUMN_ID + " = ?",
                new String[]{String.valueOf(categoryID)});
        db.close();

        return rowsAffected; // Trả về số hàng bị xóa
    }

    public int updateCategory(int categoryID, String categoryName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CATEGORY_COLUMN_NAME, categoryName);

        // Cập nhật hàng dựa trên ID
        int rowsAffected = db.update(DatabaseHelper.CATEGORY_TABLE_NAME,
                values,
                DatabaseHelper.CATEGORY_COLUMN_ID + " = ?",
                new String[]{String.valueOf(categoryID)});
        db.close();

        return rowsAffected; // Trả về số hàng bị cập nhật
    }


    public long addCategory(Category category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CATEGORY_COLUMN_NAME, category.getCategoryName());

        // Trả về ID của hàng được chèn hoặc -1 nếu có lỗi
        long id = db.insert(DatabaseHelper.CATEGORY_TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public String getCategoryNameById(int categoryId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.query("category", new String[]{"categoryName"}, "categoryID = ?", new String[]{String.valueOf(categoryId)}, null, null, null);

        Cursor cursor = db.query(
                DatabaseHelper.CATEGORY_TABLE_NAME,
                new String[]{DatabaseHelper.CATEGORY_COLUMN_NAME},
                DatabaseHelper.CATEGORY_COLUMN_ID + " = ?",
                new String[]{String.valueOf(categoryId)},
                null,
                null,
                null
        );


        if (cursor != null && cursor.moveToFirst()) {
            String categoryName = cursor.getString(cursor.getColumnIndex("categoryName"));
            cursor.close();
            return categoryName;
        }
        return "Unknown";
    }

    public List<String> getAllCategoryNames() {
        List<String> categoryList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                DatabaseHelper.CATEGORY_COLUMN_NAME,
        };

        Cursor cursor = db.query(DatabaseHelper.CATEGORY_TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CATEGORY_COLUMN_NAME));

                categoryList.add(name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categoryList;
    }


    public int getCategoryIdByName(String categoryName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("category", new String[]{"categoryID"}, "categoryName = ?", new String[]{categoryName}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int categoryId = cursor.getInt(cursor.getColumnIndex("categoryID"));
            cursor.close();
            return categoryId;
        }
        return -1; // or handle this case as needed
    }
}
