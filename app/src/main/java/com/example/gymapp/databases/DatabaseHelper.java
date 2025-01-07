package com.example.gymapp.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gym.db";
    private static final int DATABASE_VERSION = 5;

    // Table and column names for Category
    public static final String CATEGORY_TABLE_NAME = "category";
    public static final String CATEGORY_COLUMN_ID = "categoryID";
    public static final String CATEGORY_COLUMN_NAME = "categoryName";
    // Table and column names for Order
    public static final String ORDER_TABLE_NAME = "orders";
    public static final String ORDER_COLUMN_ID = "orderID";
    public static final String ORDER_COLUMN_USER_ID = "userID";
    public static final String ORDER_COLUMN_TOTAL_AMOUNT = "totalAmount";
    public static final String ORDER_COLUMN_STATUS = "status";
    public static final String ORDER_COLUMN_DATE = "orderDate";
//
    // Table and column names for OrderDetails
    public static final String ORDER_DETAILS_TABLE_NAME = "orderDetails";
    public static final String ORDER_DETAILS_COLUMN_ID = "orderDetailID";
    public static final String ORDER_DETAILS_COLUMN_ORDER_ID = "orderID";
    public static final String ORDER_DETAILS_COLUMN_PRODUCT_ID = "productID";
    public static final String ORDER_DETAILS_COLUMN_QUANTITY = "quantity";
    public static final String ORDER_DETAILS_COLUMN_UNIT_PRICE = "unitPrice";
    public static final String ORDER_DETAILS_COLUMN_TOTAL_PRICE = "totalPrice";
//
    // Table and column names for Product
    public static final String PRODUCT_TABLE_NAME = "product";
    public static final String PRODUCT_COLUMN_ID = "id";
    public static final String PRODUCT_COLUMN_NAME = "name";
    public static final String PRODUCT_COLUMN_PRICE = "price";
    public static final String PRODUCT_COLUMN_QUANTITY = "quantity";
    public static final String PRODUCT_COLUMN_CATEGORY_ID = "categoryID";
    public static final String PRODUCT_COLUMN_IMAGE = "image";
//

    // Table and column names for User
    public static final String USER_TABLE_NAME = "user";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_USERNAME = "username";
    public static final String USER_COLUMN_FULLNAME = "fullname";
    public static final String USER_COLUMN_PASSWORD = "password";
    public static final String USER_COLUMN_ROLE = "role";
    public static final String USER_COLUMN_EMAIL = "email";
    public static final String USER_COLUMN_BIRTHDAY = "birthday";
    public static final String USER_COLUMN_GENDER = "gender";
    public static final String USER_COLUMN_AVATAR = "avatar";

    // SQL queries to create tables
    private static final String CREATE_CATEGORY_TABLE =
            "CREATE TABLE " + CATEGORY_TABLE_NAME + " (" +
                    CATEGORY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CATEGORY_COLUMN_NAME + " TEXT NOT NULL);";
//
    private static final String CREATE_ORDER_TABLE =
            "CREATE TABLE " + ORDER_TABLE_NAME + " (" +
                    ORDER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ORDER_COLUMN_USER_ID + " INTEGER NOT NULL, " +
                    ORDER_COLUMN_TOTAL_AMOUNT + " REAL NOT NULL, " +
                    ORDER_COLUMN_STATUS + " TEXT NOT NULL, " +
                    ORDER_COLUMN_DATE + " TEXT NOT NULL);";

    private static final String CREATE_ORDER_DETAILS_TABLE =
            "CREATE TABLE " + ORDER_DETAILS_TABLE_NAME + " (" +
                    ORDER_DETAILS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ORDER_DETAILS_COLUMN_ORDER_ID + " INTEGER NOT NULL, " +
                    ORDER_DETAILS_COLUMN_PRODUCT_ID + " INTEGER NOT NULL, " +
                    ORDER_DETAILS_COLUMN_QUANTITY + " INTEGER NOT NULL, " +
                    ORDER_DETAILS_COLUMN_UNIT_PRICE + " REAL NOT NULL, " +
                    ORDER_DETAILS_COLUMN_TOTAL_PRICE + " REAL NOT NULL, " +
                    "FOREIGN KEY (" + ORDER_DETAILS_COLUMN_ORDER_ID + ") REFERENCES " + ORDER_TABLE_NAME + "(" + ORDER_COLUMN_ID + "), " +
                    "FOREIGN KEY (" + ORDER_DETAILS_COLUMN_PRODUCT_ID + ") REFERENCES " + PRODUCT_TABLE_NAME + "(" + PRODUCT_COLUMN_ID + "));";

    private static final String CREATE_PRODUCT_TABLE =
            "CREATE TABLE " + PRODUCT_TABLE_NAME + " (" +
                    PRODUCT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PRODUCT_COLUMN_NAME + " TEXT NOT NULL, " +
                    PRODUCT_COLUMN_PRICE + " REAL NOT NULL, " +
                    PRODUCT_COLUMN_QUANTITY + " INTEGER NOT NULL, " +
                    PRODUCT_COLUMN_CATEGORY_ID + " INTEGER NOT NULL, " +
                    PRODUCT_COLUMN_IMAGE + " TEXT, " +
                    "FOREIGN KEY (" + PRODUCT_COLUMN_CATEGORY_ID + ") REFERENCES " + CATEGORY_TABLE_NAME + "(" + CATEGORY_COLUMN_ID + "));";
//
    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_COLUMN_USERNAME + " TEXT NOT NULL, " +
                    USER_COLUMN_FULLNAME + " TEXT NOT NULL, " +
                    USER_COLUMN_PASSWORD + " TEXT NOT NULL, " +
                    USER_COLUMN_ROLE + " TEXT NOT NULL, " +
                    USER_COLUMN_EMAIL + " TEXT NOT NULL, " +
                    USER_COLUMN_BIRTHDAY + " TEXT, " +
                    USER_COLUMN_GENDER + " TEXT, " +
                    USER_COLUMN_AVATAR + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_ORDER_TABLE);
        db.execSQL(CREATE_ORDER_DETAILS_TABLE);
        insertSampleDataUsers(db);
        insertSampleDataCategories(db);
        insertSampleDataProducts(db);
        insertSampleDataOrders(db);
        insertSampleDataOrderDetails(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ORDER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ORDER_DETAILS_TABLE_NAME);

        onCreate(db);
    }

    private void insertSampleDataUsers(SQLiteDatabase db) {
        // User 1
        ContentValues values1 = new ContentValues();
        values1.put(USER_COLUMN_USERNAME, "john_doe");
        values1.put(USER_COLUMN_FULLNAME, "John Doe");
        values1.put(USER_COLUMN_PASSWORD, "password123");
        values1.put(USER_COLUMN_ROLE, "Admin");
        values1.put(USER_COLUMN_EMAIL, "john.doe@example.com");
        values1.put(USER_COLUMN_BIRTHDAY, "1990-01-01");
        values1.put(USER_COLUMN_GENDER, "Male");
        db.insert(USER_TABLE_NAME, null, values1);

        // User 2
        ContentValues values2 = new ContentValues();
        values2.put(USER_COLUMN_USERNAME, "jane_smith");
        values2.put(USER_COLUMN_FULLNAME, "Jane Smith");
        values2.put(USER_COLUMN_PASSWORD, "password456");
        values2.put(USER_COLUMN_ROLE, "Customer");
        values2.put(USER_COLUMN_EMAIL, "jane.smith@example.com");
        values2.put(USER_COLUMN_BIRTHDAY, "1985-05-15");
        values2.put(USER_COLUMN_GENDER, "Female");
        db.insert(USER_TABLE_NAME, null, values2);

        // User 3
        ContentValues values3 = new ContentValues();
        values3.put(USER_COLUMN_USERNAME, "alice_johnson");
        values3.put(USER_COLUMN_FULLNAME, "Alice Johnson");
        values3.put(USER_COLUMN_PASSWORD, "password789");
        values3.put(USER_COLUMN_ROLE, "Customer");
        values3.put(USER_COLUMN_EMAIL, "alice.johnson@example.com");
        values3.put(USER_COLUMN_BIRTHDAY, "1992-07-20");
        values3.put(USER_COLUMN_GENDER, "Female");
        db.insert(USER_TABLE_NAME, null, values3);

        // User 4
        ContentValues values4 = new ContentValues();
        values4.put(USER_COLUMN_USERNAME, "bob_brown");
        values4.put(USER_COLUMN_FULLNAME, "Bob Brown");
        values4.put(USER_COLUMN_PASSWORD, "password000");
        values4.put(USER_COLUMN_ROLE, "Customer");
        values4.put(USER_COLUMN_EMAIL, "bob.brown@example.com");
        values4.put(USER_COLUMN_BIRTHDAY, "1988-12-10");
        values4.put(USER_COLUMN_GENDER, "Male");
        db.insert(USER_TABLE_NAME, null, values4);

        // User 5
        ContentValues values5 = new ContentValues();
        values5.put(USER_COLUMN_USERNAME, "charlie_davis");
        values5.put(USER_COLUMN_FULLNAME, "Charlie Davis");
        values5.put(USER_COLUMN_PASSWORD, "password111");
        values5.put(USER_COLUMN_ROLE, "Customer");
        values5.put(USER_COLUMN_EMAIL, "charlie.davis@example.com");
        values5.put(USER_COLUMN_BIRTHDAY, "1993-03-30");
        values5.put(USER_COLUMN_GENDER, "Non-binary");
        db.insert(USER_TABLE_NAME, null, values5);

        // User 6
        ContentValues values6 = new ContentValues();
        values6.put(USER_COLUMN_USERNAME, "daisy_martin");
        values6.put(USER_COLUMN_FULLNAME, "Daisy Martin");
        values6.put(USER_COLUMN_PASSWORD, "password222");
        values6.put(USER_COLUMN_ROLE, "Customer");
        values6.put(USER_COLUMN_EMAIL, "daisy.martin@example.com");
        values6.put(USER_COLUMN_BIRTHDAY, "1994-09-12");
        values6.put(USER_COLUMN_GENDER, "Female");
        db.insert(USER_TABLE_NAME, null, values6);

        // User 7
        ContentValues values7 = new ContentValues();
        values7.put(USER_COLUMN_USERNAME, "edward_wilson");
        values7.put(USER_COLUMN_FULLNAME, "Edward Wilson");
        values7.put(USER_COLUMN_PASSWORD, "password333");
        values7.put(USER_COLUMN_ROLE, "Customer");
        values7.put(USER_COLUMN_EMAIL, "edward.wilson@example.com");
        values7.put(USER_COLUMN_BIRTHDAY, "1991-04-22");
        values7.put(USER_COLUMN_GENDER, "Male");
        db.insert(USER_TABLE_NAME, null, values7);

        // User 8
        ContentValues values8 = new ContentValues();
        values8.put(USER_COLUMN_USERNAME, "fiona_clark");
        values8.put(USER_COLUMN_FULLNAME, "Fiona Clark");
        values8.put(USER_COLUMN_PASSWORD, "password444");
        values8.put(USER_COLUMN_ROLE, "Customer");
        values8.put(USER_COLUMN_EMAIL, "fiona.clark@example.com");
        values8.put(USER_COLUMN_BIRTHDAY, "1995-06-18");
        values8.put(USER_COLUMN_GENDER, "Female");
        db.insert(USER_TABLE_NAME, null, values8);

        // User 9
        ContentValues values9 = new ContentValues();
        values9.put(USER_COLUMN_USERNAME, "george_lee");
        values9.put(USER_COLUMN_FULLNAME, "George Lee");
        values9.put(USER_COLUMN_PASSWORD, "password555");
        values9.put(USER_COLUMN_ROLE, "PT");
        values9.put(USER_COLUMN_EMAIL, "george.lee@example.com");
        values9.put(USER_COLUMN_BIRTHDAY, "1987-11-05");
        values9.put(USER_COLUMN_GENDER, "Male");
        db.insert(USER_TABLE_NAME, null, values9);

        // User 10
        ContentValues values10 = new ContentValues();
        values10.put(USER_COLUMN_USERNAME, "hannah_moore");
        values10.put(USER_COLUMN_FULLNAME, "Hannah Moore");
        values10.put(USER_COLUMN_PASSWORD, "password666");
        values10.put(USER_COLUMN_ROLE, "PT");
        values10.put(USER_COLUMN_EMAIL, "hannah.moore@example.com");
        values10.put(USER_COLUMN_BIRTHDAY, "1989-10-24");
        values10.put(USER_COLUMN_GENDER, "Female");
        db.insert(USER_TABLE_NAME, null, values10);
    }

    private void insertSampleDataCategories(SQLiteDatabase db) {
        // Danh mục 1
        ContentValues values1 = new ContentValues();
        values1.put(CATEGORY_COLUMN_NAME, "Thiết bị Cardio");
        db.insert(CATEGORY_TABLE_NAME, null, values1);

        // Danh mục 2
        ContentValues values2 = new ContentValues();
        values2.put(CATEGORY_COLUMN_NAME, "Thiết bị Tạ");
        db.insert(CATEGORY_TABLE_NAME, null, values2);

        // Danh mục 3
        ContentValues values3 = new ContentValues();
        values3.put(CATEGORY_COLUMN_NAME, "Dụng cụ Yoga");
        db.insert(CATEGORY_TABLE_NAME, null, values3);

        // Danh mục 4
        ContentValues values4 = new ContentValues();
        values4.put(CATEGORY_COLUMN_NAME, "Dụng cụ Pilates");
        db.insert(CATEGORY_TABLE_NAME, null, values4);

        // Danh mục 5
        ContentValues values5 = new ContentValues();
        values5.put(CATEGORY_COLUMN_NAME, "Dây Kháng Lực");
        db.insert(CATEGORY_TABLE_NAME, null, values5);

        // Danh mục 6
        ContentValues values6 = new ContentValues();
        values6.put(CATEGORY_COLUMN_NAME, "Tạ Tự Do");
        db.insert(CATEGORY_TABLE_NAME, null, values6);

        // Danh mục 7
        ContentValues values7 = new ContentValues();
        values7.put(CATEGORY_COLUMN_NAME, "Máy Tập Toàn Thân");
        db.insert(CATEGORY_TABLE_NAME, null, values7);

        // Danh mục 8
        ContentValues values8 = new ContentValues();
        values8.put(CATEGORY_COLUMN_NAME, "Máy Tập Cánh Tay");
        db.insert(CATEGORY_TABLE_NAME, null, values8);

        // Danh mục 9
        ContentValues values9 = new ContentValues();
        values9.put(CATEGORY_COLUMN_NAME, "Máy Tập Chân");
        db.insert(CATEGORY_TABLE_NAME, null, values9);

        // Danh mục 10
        ContentValues values10 = new ContentValues();
        values10.put(CATEGORY_COLUMN_NAME, "Phụ kiện Gym");
        db.insert(CATEGORY_TABLE_NAME, null, values10);
    }

    private void insertSampleDataProducts(SQLiteDatabase db) {
        // Product 1
        ContentValues values1 = new ContentValues();
        values1.put(PRODUCT_COLUMN_NAME, "Máy Chạy Bộ");
        values1.put(PRODUCT_COLUMN_PRICE, 1500.00);
        values1.put(PRODUCT_COLUMN_QUANTITY, 10);
        values1.put(PRODUCT_COLUMN_CATEGORY_ID, 1); // Assuming this category ID exists
        db.insert(PRODUCT_TABLE_NAME, null, values1);

        // Product 2
        ContentValues values2 = new ContentValues();
        values2.put(PRODUCT_COLUMN_NAME, "Tạ Đơn");
        values2.put(PRODUCT_COLUMN_PRICE, 200.00);
        values2.put(PRODUCT_COLUMN_QUANTITY, 20);
        values2.put(PRODUCT_COLUMN_CATEGORY_ID, 2);
        db.insert(PRODUCT_TABLE_NAME, null, values2);

        // Product 3
        ContentValues values3 = new ContentValues();
        values3.put(PRODUCT_COLUMN_NAME, "Thảm Yoga");
        values3.put(PRODUCT_COLUMN_PRICE, 50.00);
        values3.put(PRODUCT_COLUMN_QUANTITY, 15);
        values3.put(PRODUCT_COLUMN_CATEGORY_ID, 3);
        db.insert(PRODUCT_TABLE_NAME, null, values3);

        // Product 4
        ContentValues values4 = new ContentValues();
        values4.put(PRODUCT_COLUMN_NAME, "Bóng Pilates");
        values4.put(PRODUCT_COLUMN_PRICE, 80.00);
        values4.put(PRODUCT_COLUMN_QUANTITY, 12);
        values4.put(PRODUCT_COLUMN_CATEGORY_ID, 4);
        db.insert(PRODUCT_TABLE_NAME, null, values4);

        // Product 5
        ContentValues values5 = new ContentValues();
        values5.put(PRODUCT_COLUMN_NAME, "Dây Kháng Lực");
        values5.put(PRODUCT_COLUMN_PRICE, 30.00);
        values5.put(PRODUCT_COLUMN_QUANTITY, 25);
        values5.put(PRODUCT_COLUMN_CATEGORY_ID, 5);
        db.insert(PRODUCT_TABLE_NAME, null, values5);

        // Product 6
        ContentValues values6 = new ContentValues();
        values6.put(PRODUCT_COLUMN_NAME, "Tạ Kettlebell");
        values6.put(PRODUCT_COLUMN_PRICE, 120.00);
        values6.put(PRODUCT_COLUMN_QUANTITY, 18);
        values6.put(PRODUCT_COLUMN_CATEGORY_ID, 6);
        db.insert(PRODUCT_TABLE_NAME, null, values6);

        // Product 7
        ContentValues values7 = new ContentValues();
        values7.put(PRODUCT_COLUMN_NAME, "Máy Tập Toàn Thân");
        values7.put(PRODUCT_COLUMN_PRICE, 2500.00);
        values7.put(PRODUCT_COLUMN_QUANTITY, 8);
        values7.put(PRODUCT_COLUMN_CATEGORY_ID, 7);
        db.insert(PRODUCT_TABLE_NAME, null, values7);

        // Product 8
        ContentValues values8 = new ContentValues();
        values8.put(PRODUCT_COLUMN_NAME, "Máy Tập Cánh Tay");
        values8.put(PRODUCT_COLUMN_PRICE, 2000.00);
        values8.put(PRODUCT_COLUMN_QUANTITY, 10);
        values8.put(PRODUCT_COLUMN_CATEGORY_ID, 8);
        db.insert(PRODUCT_TABLE_NAME, null, values8);

        // Product 9
        ContentValues values9 = new ContentValues();
        values9.put(PRODUCT_COLUMN_NAME, "Máy Tập Chân");
        values9.put(PRODUCT_COLUMN_PRICE, 1800.00);
        values9.put(PRODUCT_COLUMN_QUANTITY, 9);
        values9.put(PRODUCT_COLUMN_CATEGORY_ID, 9);
        db.insert(PRODUCT_TABLE_NAME, null, values9);

        // Product 10
        ContentValues values10 = new ContentValues();
        values10.put(PRODUCT_COLUMN_NAME, "Dây Tập Kéo");
        values10.put(PRODUCT_COLUMN_PRICE, 40.00);
        values10.put(PRODUCT_COLUMN_QUANTITY, 30);
        values10.put(PRODUCT_COLUMN_CATEGORY_ID, 5);
        db.insert(PRODUCT_TABLE_NAME, null, values10);

        // Product 11
        ContentValues values11 = new ContentValues();
        values11.put(PRODUCT_COLUMN_NAME, "Tạ Đôi");
        values11.put(PRODUCT_COLUMN_PRICE, 250.00);
        values11.put(PRODUCT_COLUMN_QUANTITY, 14);
        values11.put(PRODUCT_COLUMN_CATEGORY_ID, 2);
        db.insert(PRODUCT_TABLE_NAME, null, values11);

        // Product 12
        ContentValues values12 = new ContentValues();
        values12.put(PRODUCT_COLUMN_NAME, "Thảm Yoga Cao Cấp");
        values12.put(PRODUCT_COLUMN_PRICE, 75.00);
        values12.put(PRODUCT_COLUMN_QUANTITY, 16);
        values12.put(PRODUCT_COLUMN_CATEGORY_ID, 3);
        db.insert(PRODUCT_TABLE_NAME, null, values12);

        // Product 13
        ContentValues values13 = new ContentValues();
        values13.put(PRODUCT_COLUMN_NAME, "Máy Đạp Xe");
        values13.put(PRODUCT_COLUMN_PRICE, 1300.00);
        values13.put(PRODUCT_COLUMN_QUANTITY, 7);
        values13.put(PRODUCT_COLUMN_CATEGORY_ID, 1);
        db.insert(PRODUCT_TABLE_NAME, null, values13);

        // Product 14
        ContentValues values14 = new ContentValues();
        values14.put(PRODUCT_COLUMN_NAME, "Bóng Tập Lưng");
        values14.put(PRODUCT_COLUMN_PRICE, 60.00);
        values14.put(PRODUCT_COLUMN_QUANTITY, 20);
        values14.put(PRODUCT_COLUMN_CATEGORY_ID, 4);
        db.insert(PRODUCT_TABLE_NAME, null, values14);

        // Product 15
        ContentValues values15 = new ContentValues();
        values15.put(PRODUCT_COLUMN_NAME, "Máy Tập Cơ Bụng");
        values15.put(PRODUCT_COLUMN_PRICE, 1500.00);
        values15.put(PRODUCT_COLUMN_QUANTITY, 6);
        values15.put(PRODUCT_COLUMN_CATEGORY_ID, 7);
        db.insert(PRODUCT_TABLE_NAME, null, values15);

        // Product 16
        ContentValues values16 = new ContentValues();
        values16.put(PRODUCT_COLUMN_NAME, "Máy Tập Gân");
        values16.put(PRODUCT_COLUMN_PRICE, 1400.00);
        values16.put(PRODUCT_COLUMN_QUANTITY, 5);
        values16.put(PRODUCT_COLUMN_CATEGORY_ID, 8);
        db.insert(PRODUCT_TABLE_NAME, null, values16);

        // Product 17
        ContentValues values17 = new ContentValues();
        values17.put(PRODUCT_COLUMN_NAME, "Tạ Tròn");
        values17.put(PRODUCT_COLUMN_PRICE, 220.00);
        values17.put(PRODUCT_COLUMN_QUANTITY, 22);
        values17.put(PRODUCT_COLUMN_CATEGORY_ID, 6);
        db.insert(PRODUCT_TABLE_NAME, null, values17);

        // Product 18
        ContentValues values18 = new ContentValues();
        values18.put(PRODUCT_COLUMN_NAME, "Máy Tập Đứng");
        values18.put(PRODUCT_COLUMN_PRICE, 1700.00);
        values18.put(PRODUCT_COLUMN_QUANTITY, 9);
        values18.put(PRODUCT_COLUMN_CATEGORY_ID, 9);
        db.insert(PRODUCT_TABLE_NAME, null, values18);

        // Product 19
        ContentValues values19 = new ContentValues();
        values19.put(PRODUCT_COLUMN_NAME, "Tạ Tự Do");
        values19.put(PRODUCT_COLUMN_PRICE, 280.00);
        values19.put(PRODUCT_COLUMN_QUANTITY, 17);
        values19.put(PRODUCT_COLUMN_CATEGORY_ID, 6);
        db.insert(PRODUCT_TABLE_NAME, null, values19);

        // Product 20
        ContentValues values20 = new ContentValues();
        values20.put(PRODUCT_COLUMN_NAME, "Máy Tập Cơ Tay");
        values20.put(PRODUCT_COLUMN_PRICE, 1600.00);
        values20.put(PRODUCT_COLUMN_QUANTITY, 11);
        values20.put(PRODUCT_COLUMN_CATEGORY_ID, 8);
        db.insert(PRODUCT_TABLE_NAME, null, values20);
    }

    private void insertSampleDataOrders(SQLiteDatabase db) {
        // Order 1
        ContentValues values1 = new ContentValues();
        values1.put(ORDER_COLUMN_USER_ID, 1); // Assuming User ID 1 exists
        values1.put(ORDER_COLUMN_TOTAL_AMOUNT, 1600.00);
        values1.put(ORDER_COLUMN_STATUS, "Completed");
        values1.put(ORDER_COLUMN_DATE, "2024-08-01");
        db.insert(ORDER_TABLE_NAME, null, values1);

        // Order 2
        ContentValues values2 = new ContentValues();
        values2.put(ORDER_COLUMN_USER_ID, 2);
        values2.put(ORDER_COLUMN_TOTAL_AMOUNT, 250.00);
        values2.put(ORDER_COLUMN_STATUS, "Pending");
        values2.put(ORDER_COLUMN_DATE, "2024-08-02");
        db.insert(ORDER_TABLE_NAME, null, values2);

        // Order 3
        ContentValues values3 = new ContentValues();
        values3.put(ORDER_COLUMN_USER_ID, 3);
        values3.put(ORDER_COLUMN_TOTAL_AMOUNT, 75.00);
        values3.put(ORDER_COLUMN_STATUS, "Shipped");
        values3.put(ORDER_COLUMN_DATE, "2024-08-03");
        db.insert(ORDER_TABLE_NAME, null, values3);

        // Order 4
        ContentValues values4 = new ContentValues();
        values4.put(ORDER_COLUMN_USER_ID, 4);
        values4.put(ORDER_COLUMN_TOTAL_AMOUNT, 1600.00);
        values4.put(ORDER_COLUMN_STATUS, "Completed");
        values4.put(ORDER_COLUMN_DATE, "2024-08-04");
        db.insert(ORDER_TABLE_NAME, null, values4);

        // Order 5
        ContentValues values5 = new ContentValues();
        values5.put(ORDER_COLUMN_USER_ID, 5);
        values5.put(ORDER_COLUMN_TOTAL_AMOUNT, 220.00);
        values5.put(ORDER_COLUMN_STATUS, "Pending");
        values5.put(ORDER_COLUMN_DATE, "2024-08-05");
        db.insert(ORDER_TABLE_NAME, null, values5);

        // Order 6
        ContentValues values6 = new ContentValues();
        values6.put(ORDER_COLUMN_USER_ID, 6);
        values6.put(ORDER_COLUMN_TOTAL_AMOUNT, 150.00);
        values6.put(ORDER_COLUMN_STATUS, "Shipped");
        values6.put(ORDER_COLUMN_DATE, "2024-08-06");
        db.insert(ORDER_TABLE_NAME, null, values6);

        // Order 7
        ContentValues values7 = new ContentValues();
        values7.put(ORDER_COLUMN_USER_ID, 7);
        values7.put(ORDER_COLUMN_TOTAL_AMOUNT, 1400.00);
        values7.put(ORDER_COLUMN_STATUS, "Completed");
        values7.put(ORDER_COLUMN_DATE, "2024-08-07");
        db.insert(ORDER_TABLE_NAME, null, values7);

        // Order 8
        ContentValues values8 = new ContentValues();
        values8.put(ORDER_COLUMN_USER_ID, 8);
        values8.put(ORDER_COLUMN_TOTAL_AMOUNT, 60.00);
        values8.put(ORDER_COLUMN_STATUS, "Pending");
        values8.put(ORDER_COLUMN_DATE, "2024-08-08");
        db.insert(ORDER_TABLE_NAME, null, values8);

        // Order 9
        ContentValues values9 = new ContentValues();
        values9.put(ORDER_COLUMN_USER_ID, 9);
        values9.put(ORDER_COLUMN_TOTAL_AMOUNT, 1500.00);
        values9.put(ORDER_COLUMN_STATUS, "Shipped");
        values9.put(ORDER_COLUMN_DATE, "2024-08-09");
        db.insert(ORDER_TABLE_NAME, null, values9);

        // Order 10
        ContentValues values10 = new ContentValues();
        values10.put(ORDER_COLUMN_USER_ID, 10);
        values10.put(ORDER_COLUMN_TOTAL_AMOUNT, 1800.00);
        values10.put(ORDER_COLUMN_STATUS, "Completed");
        values10.put(ORDER_COLUMN_DATE, "2024-08-10");
        db.insert(ORDER_TABLE_NAME, null, values10);
    }

    private void insertSampleDataOrderDetails(SQLiteDatabase db) {
        // OrderDetail 1
        ContentValues values1 = new ContentValues();
        values1.put(ORDER_DETAILS_COLUMN_ORDER_ID, 1); // Assuming Order ID 1 exists
        values1.put(ORDER_DETAILS_COLUMN_PRODUCT_ID, 1); // Assuming Product ID 1 exists
        values1.put(ORDER_DETAILS_COLUMN_QUANTITY, 1);
        values1.put(ORDER_DETAILS_COLUMN_UNIT_PRICE, 1500.00);
        values1.put(ORDER_DETAILS_COLUMN_TOTAL_PRICE, 1500.00);
        db.insert(ORDER_DETAILS_TABLE_NAME, null, values1);

        // OrderDetail 2
        ContentValues values2 = new ContentValues();
        values2.put(ORDER_DETAILS_COLUMN_ORDER_ID, 1);
        values2.put(ORDER_DETAILS_COLUMN_PRODUCT_ID, 2);
        values2.put(ORDER_DETAILS_COLUMN_QUANTITY, 1);
        values2.put(ORDER_DETAILS_COLUMN_UNIT_PRICE, 100.00);
        values2.put(ORDER_DETAILS_COLUMN_TOTAL_PRICE, 100.00);
        db.insert(ORDER_DETAILS_TABLE_NAME, null, values2);

        // OrderDetail 3
        ContentValues values3 = new ContentValues();
        values3.put(ORDER_DETAILS_COLUMN_ORDER_ID, 2);
        values3.put(ORDER_DETAILS_COLUMN_PRODUCT_ID, 3);
        values3.put(ORDER_DETAILS_COLUMN_QUANTITY, 1);
        values3.put(ORDER_DETAILS_COLUMN_UNIT_PRICE, 75.00);
        values3.put(ORDER_DETAILS_COLUMN_TOTAL_PRICE, 75.00);
        db.insert(ORDER_DETAILS_TABLE_NAME, null, values3);

        // OrderDetail 4
        ContentValues values4 = new ContentValues();
        values4.put(ORDER_DETAILS_COLUMN_ORDER_ID, 3);
        values4.put(ORDER_DETAILS_COLUMN_PRODUCT_ID, 4);
        values4.put(ORDER_DETAILS_COLUMN_QUANTITY, 1);
        values4.put(ORDER_DETAILS_COLUMN_UNIT_PRICE, 80.00);
        values4.put(ORDER_DETAILS_COLUMN_TOTAL_PRICE, 80.00);
        db.insert(ORDER_DETAILS_TABLE_NAME, null, values4);

        // OrderDetail 5
        ContentValues values5 = new ContentValues();
        values5.put(ORDER_DETAILS_COLUMN_ORDER_ID, 4);
        values5.put(ORDER_DETAILS_COLUMN_PRODUCT_ID, 5);
        values5.put(ORDER_DETAILS_COLUMN_QUANTITY, 1);
        values5.put(ORDER_DETAILS_COLUMN_UNIT_PRICE, 30.00);
        values5.put(ORDER_DETAILS_COLUMN_TOTAL_PRICE, 30.00);
        db.insert(ORDER_DETAILS_TABLE_NAME, null, values5);

        // OrderDetail 6
        ContentValues values6 = new ContentValues();
        values6.put(ORDER_DETAILS_COLUMN_ORDER_ID, 5);
        values6.put(ORDER_DETAILS_COLUMN_PRODUCT_ID, 6);
        values6.put(ORDER_DETAILS_COLUMN_QUANTITY, 2);
        values6.put(ORDER_DETAILS_COLUMN_UNIT_PRICE, 120.00);
        values6.put(ORDER_DETAILS_COLUMN_TOTAL_PRICE, 240.00);
        db.insert(ORDER_DETAILS_TABLE_NAME, null, values6);

        // OrderDetail 7
        ContentValues values7 = new ContentValues();
        values7.put(ORDER_DETAILS_COLUMN_ORDER_ID, 6);
        values7.put(ORDER_DETAILS_COLUMN_PRODUCT_ID, 7);
        values7.put(ORDER_DETAILS_COLUMN_QUANTITY, 1);
        values7.put(ORDER_DETAILS_COLUMN_UNIT_PRICE, 2500.00);
        values7.put(ORDER_DETAILS_COLUMN_TOTAL_PRICE, 2500.00);
        db.insert(ORDER_DETAILS_TABLE_NAME, null, values7);

        // OrderDetail 8
        ContentValues values8 = new ContentValues();
        values8.put(ORDER_DETAILS_COLUMN_ORDER_ID, 7);
        values8.put(ORDER_DETAILS_COLUMN_PRODUCT_ID, 8);
        values8.put(ORDER_DETAILS_COLUMN_QUANTITY, 1);
        values8.put(ORDER_DETAILS_COLUMN_UNIT_PRICE, 2000.00);
        values8.put(ORDER_DETAILS_COLUMN_TOTAL_PRICE, 2000.00);
        db.insert(ORDER_DETAILS_TABLE_NAME, null, values8);

        // OrderDetail 9
        ContentValues values9 = new ContentValues();
        values9.put(ORDER_DETAILS_COLUMN_ORDER_ID, 8);
        values9.put(ORDER_DETAILS_COLUMN_PRODUCT_ID, 9);
        values9.put(ORDER_DETAILS_COLUMN_QUANTITY, 1);
        values9.put(ORDER_DETAILS_COLUMN_UNIT_PRICE, 1800.00);
        values9.put(ORDER_DETAILS_COLUMN_TOTAL_PRICE, 1800.00);
        db.insert(ORDER_DETAILS_TABLE_NAME, null, values9);

        // OrderDetail 10
        ContentValues values10 = new ContentValues();
        values10.put(ORDER_DETAILS_COLUMN_ORDER_ID, 9);
        values10.put(ORDER_DETAILS_COLUMN_PRODUCT_ID, 10);
        values10.put(ORDER_DETAILS_COLUMN_QUANTITY, 1);
        values10.put(ORDER_DETAILS_COLUMN_UNIT_PRICE, 40.00);
        values10.put(ORDER_DETAILS_COLUMN_TOTAL_PRICE, 40.00);
        db.insert(ORDER_DETAILS_TABLE_NAME, null, values10);
    }

}
