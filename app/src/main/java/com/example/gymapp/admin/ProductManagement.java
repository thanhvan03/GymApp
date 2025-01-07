package com.example.gymapp.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymapp.R;
import com.example.gymapp.adapter.ProductAdapter;
import com.example.gymapp.databases.ProductDatabase;
import com.example.gymapp.model.Product;

import java.util.List;

public class ProductManagement extends AppCompatActivity {

    private ListView listViewProducts;
    private ProductDatabase productDatabase;
    private List<Product> productList;
    private ProductAdapter productAdapter;
    private Button buttonReturn;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_product_management);

//        int dbVersion = DatabaseVersionChecker.getDatabaseVersion(this);
//
//        TextView versionTextView = findViewById(R.id.textViewProducts);
//        versionTextView.setText("Database Version: " + dbVersion);

        productDatabase = new ProductDatabase(this);

        listViewProducts = findViewById(R.id.listViewProducts);
        buttonReturn = findViewById(R.id.buttonReturn);
        buttonAdd = findViewById(R.id.buttonAdd);

        productList = productDatabase.getAllProducts();
        productAdapter = new ProductAdapter(this, productList);
        listViewProducts.setAdapter(productAdapter);

        buttonAdd.setOnClickListener(v -> {
            Intent intent = new Intent(ProductManagement.this, ProductCreate.class);
            startActivityForResult(intent, 1);
        });

        buttonReturn.setOnClickListener(v -> {
            finish();
        });

        listViewProducts.setOnItemLongClickListener((parent, view, position, id) -> {
            Product selectedProduct = productList.get(position);

            // Open DetailActivity with the selected user's information
            Intent intent = new Intent(ProductManagement.this, ProductDetail.class);
            intent.putExtra("product_id", selectedProduct.getId());
//            startActivity(intent);
            startActivityForResult(intent, 1);

            return true; // Indicates that the long-click was handled
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Làm mới danh sách sản phẩm
            productList = productDatabase.getAllProducts();
            productAdapter = new ProductAdapter(this, productList);
            listViewProducts.setAdapter(productAdapter);
        }
    }


}