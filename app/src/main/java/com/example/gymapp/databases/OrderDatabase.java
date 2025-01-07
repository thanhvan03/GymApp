package com.example.gymapp.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.gymapp.model.Order;
import com.example.gymapp.model.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderDatabase {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public OrderDatabase(Context context) {
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

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        Cursor cursor = null;
        try {
            open();
            String query = "SELECT * FROM orders";
            cursor = database.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    Order order = new Order();
                    order.setOrderID(cursor.getInt(cursor.getColumnIndex("orderID")));
                    order.setUserID(cursor.getInt(cursor.getColumnIndex("userID")));
                    order.setTotalAmount(cursor.getDouble(cursor.getColumnIndex("totalAmount")));
                    order.setStatus(cursor.getString(cursor.getColumnIndex("status")));

                    String orderDateStr = cursor.getString(cursor.getColumnIndex("orderDate"));
                    if (orderDateStr != null) {
                        try {
                            Date orderDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(orderDateStr);
                            order.setOrderDate(orderDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    orders.add(order);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            close();
        }
        return orders;
    }

    public Order getOrderById(int orderId) {
        Order order = null;
        Cursor cursor = null;
        try {
            open();
            String query = "SELECT orders.orderID, orders.userID, orders.totalAmount, orders.status, orders.orderDate, user.userName " +
                    "FROM orders " +
                    "INNER JOIN user ON orders.userID = user.id " +
                    "WHERE orders.orderID = ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(orderId)});

            if (cursor.moveToFirst()) {
                order = new Order();
                order.setOrderID(cursor.getInt(cursor.getColumnIndex("orderID")));
                order.setUserID(cursor.getInt(cursor.getColumnIndex("userID")));
                order.setTotalAmount(cursor.getDouble(cursor.getColumnIndex("totalAmount")));
                order.setStatus(cursor.getString(cursor.getColumnIndex("status")));

                String orderDateStr = cursor.getString(cursor.getColumnIndex("orderDate"));
                if (orderDateStr != null) {
                    try {
                        Date orderDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(orderDateStr);
                        order.setOrderDate(orderDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                // Assuming you have userName in order object
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            close();
        }
        return order;
    }

    public List<Product> getProductsByOrderId(int orderId) {
        List<Product> productList = new ArrayList<>();
        Cursor cursor = null;
        try {
            open();
            String query = "SELECT p.* FROM " + DatabaseHelper.PRODUCT_TABLE_NAME + " p " +
                    "INNER JOIN " + DatabaseHelper.ORDER_DETAILS_TABLE_NAME + " od " +
                    "ON p." + DatabaseHelper.PRODUCT_COLUMN_ID + " = od." + DatabaseHelper.ORDER_DETAILS_COLUMN_PRODUCT_ID +
                    " WHERE od." + DatabaseHelper.ORDER_DETAILS_COLUMN_ORDER_ID + " = ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(orderId)});

            if (cursor.moveToFirst()) {
                do {
                    Product product = new Product();
                    product.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_ID)));
                    product.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_NAME)));
                    product.setPrice(cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_PRICE)));
                    product.setQuantity(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_QUANTITY)));
                    product.setCategoryID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_CATEGORY_ID)));
                    product.setImage(cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_IMAGE)));
                    productList.add(product);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            close();
        }
        return productList;
    }

    public boolean deleteOrderById(int orderId) {
        database.beginTransaction();
        try {
            int rowsDeleted = database.delete(DatabaseHelper.ORDER_DETAILS_TABLE_NAME,
                    DatabaseHelper.ORDER_DETAILS_COLUMN_ORDER_ID + " = ?",
                    new String[]{String.valueOf(orderId)});

            if (rowsDeleted > 0) {
                rowsDeleted = database.delete(DatabaseHelper.ORDER_TABLE_NAME,
                        DatabaseHelper.ORDER_COLUMN_ID + " = ?",
                        new String[]{String.valueOf(orderId)});

                database.setTransactionSuccessful();
                return rowsDeleted > 0;
            }
        } finally {
            database.endTransaction();
        }
        return false;
    }

    public boolean updateOrder(int orderId, double totalAmount, String status) {
        boolean isUpdated = false;
        try {
            open(); // Mở cơ sở dữ liệu nếu chưa mở
            database.beginTransaction();

            ContentValues values = new ContentValues();
            values.put("totalAmount", totalAmount);
            values.put("status", status);

            int rowsUpdated = database.update(DatabaseHelper.ORDER_TABLE_NAME,
                    values,
                    "orderID = ?",
                    new String[]{String.valueOf(orderId)});

            if (rowsUpdated > 0) {
                database.setTransactionSuccessful();
                isUpdated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (database != null && database.isOpen()) {
                database.endTransaction();
                close(); // Đảm bảo đóng cơ sở dữ liệu sau khi hoàn tất
            }
        }
        return isUpdated;
    }

    public boolean updateOrderDetail(int orderId, int productId, int quantity) {
        boolean isUpdated = false;
        try {
            open(); // Mở cơ sở dữ liệu nếu chưa mở
            database.beginTransaction();

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.ORDER_DETAILS_COLUMN_QUANTITY, quantity);

            int rowsUpdated = database.update(DatabaseHelper.ORDER_DETAILS_TABLE_NAME,
                    values,
                    DatabaseHelper.ORDER_DETAILS_COLUMN_ORDER_ID + " = ? AND " +
                            DatabaseHelper.ORDER_DETAILS_COLUMN_PRODUCT_ID + " = ?",
                    new String[]{String.valueOf(orderId), String.valueOf(productId)});

            if (rowsUpdated > 0) {
                database.setTransactionSuccessful();
                isUpdated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (database != null && database.isOpen()) {
                database.endTransaction();
                close(); // Đảm bảo đóng cơ sở dữ liệu sau khi hoàn tất
            }
        }
        return isUpdated;
    }


}
