package com.example.gymapp.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gymapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabase {

    private DatabaseHelper dbHelper;

    public ProductDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                DatabaseHelper.PRODUCT_COLUMN_ID,
                DatabaseHelper.PRODUCT_COLUMN_NAME,
                DatabaseHelper.PRODUCT_COLUMN_PRICE,
                DatabaseHelper.PRODUCT_COLUMN_QUANTITY,
                DatabaseHelper.PRODUCT_COLUMN_CATEGORY_ID,
                DatabaseHelper.PRODUCT_COLUMN_IMAGE
        };

        Cursor cursor = db.query(DatabaseHelper.PRODUCT_TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_NAME));
                double price = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_PRICE));
                int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_QUANTITY));
                int categoryId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_CATEGORY_ID));
                String image = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_IMAGE));

                Product product = new Product(id, name, price, quantity, image, categoryId);
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }

    public Product getProductById(int productId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                DatabaseHelper.PRODUCT_COLUMN_ID,
                DatabaseHelper.PRODUCT_COLUMN_NAME,
                DatabaseHelper.PRODUCT_COLUMN_PRICE,
                DatabaseHelper.PRODUCT_COLUMN_QUANTITY,
                DatabaseHelper.PRODUCT_COLUMN_CATEGORY_ID,
                DatabaseHelper.PRODUCT_COLUMN_IMAGE
        };
        String selection = DatabaseHelper.PRODUCT_COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(productId) };

        Cursor cursor = db.query(DatabaseHelper.PRODUCT_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_NAME));
            double price = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_PRICE));
            int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_QUANTITY));
            int categoryId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_CATEGORY_ID));
            String image = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_IMAGE));

            cursor.close();
            db.close();
            return new Product(id, name, price, quantity, image, categoryId);
        }
        cursor.close();
        db.close();
        return null; // Return null if product not found
    }

    public int deleteProduct(int productID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsAffected = db.delete(DatabaseHelper.PRODUCT_TABLE_NAME,
                DatabaseHelper.PRODUCT_COLUMN_ID + " = ?",
                new String[]{String.valueOf(productID)});
        db.close();
        return rowsAffected;
         // Return number of rows affected
    }

    public int updateProduct(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PRODUCT_COLUMN_NAME, product.getName());
        values.put(DatabaseHelper.PRODUCT_COLUMN_PRICE, product.getPrice());
        values.put(DatabaseHelper.PRODUCT_COLUMN_QUANTITY, product.getQuantity());
        values.put(DatabaseHelper.PRODUCT_COLUMN_CATEGORY_ID, product.getCategoryID());
        values.put(DatabaseHelper.PRODUCT_COLUMN_IMAGE, product.getImage());

        int rowsAffected = db.update(DatabaseHelper.PRODUCT_TABLE_NAME,
                values,
                DatabaseHelper.PRODUCT_COLUMN_ID + " = ?",
                new String[]{String.valueOf(product.getId())});
        db.close();
        return rowsAffected; // Return number of rows affected
    }

    public long addProduct(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PRODUCT_COLUMN_NAME, product.getName());
        values.put(DatabaseHelper.PRODUCT_COLUMN_PRICE, product.getPrice());
        values.put(DatabaseHelper.PRODUCT_COLUMN_QUANTITY, product.getQuantity());
        values.put(DatabaseHelper.PRODUCT_COLUMN_CATEGORY_ID, product.getCategoryID());
        values.put(DatabaseHelper.PRODUCT_COLUMN_IMAGE, product.getImage());

        long id = db.insert(DatabaseHelper.PRODUCT_TABLE_NAME, null, values);
        db.close();
        return id; // Return the ID of the inserted row
    }

}

