package com.example.gymapp.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gymapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDatabase {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public OrderDetailsDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public List<Product> getProductsForOrder(int orderID) {
        List<Product> products = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        String query = "SELECT p.id, p.name, p.price, p.quantity, p.image, p.categoryID " +
                "FROM OrderDetails od " +
                "JOIN Product p ON od.productID = p.id " +
                "WHERE od.orderID = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(orderID)});

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndex("id")));
                product.setName(cursor.getString(cursor.getColumnIndex("name")));
                product.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
                product.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
//                product.setImage(cursor.getString(cursor.getColumnIndex("image")));
                product.setCategoryID(cursor.getInt(cursor.getColumnIndex("categoryID")));
                products.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return products;
    }
}
