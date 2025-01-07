package com.example.gymapp.admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.gymapp.R;
import com.example.gymapp.databases.CategoryDatabase;
import com.example.gymapp.databases.ProductDatabase;
import com.example.gymapp.databases.UserDatabase;
import com.example.gymapp.model.Product;
import com.example.gymapp.model.User;

public class ProductDetail extends AppCompatActivity {

    private ProductDatabase productDatabase;
    private CategoryDatabase categoryDatabase;
    private int productID;
    private Product product;

    private TextView textViewName;
    private TextView textViewPrice;
    private TextView textViewQuantity;
    private TextView textViewCategory;
    private ImageView imageView;

    private Button buttonDelete;
    private Button buttonUpdate;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_product_details);

        productDatabase = new ProductDatabase(this);
        categoryDatabase = new CategoryDatabase(this);

        textViewName = findViewById(R.id.textViewName);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewQuantity = findViewById(R.id.textViewQuantity);
        textViewCategory = findViewById(R.id.textViewCategory);
        imageView = findViewById(R.id.imageView);
        buttonBack = findViewById(R.id.buttonBack);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        productID = getIntent().getIntExtra("product_id", -1);
        product = productDatabase.getProductById(productID);
//        product = productDatabase.getAllProducts().stream().filter(p -> p.getId() == productID).findFirst().orElse(null);

        if(product != null){
            textViewName.setText(product.getName());
            textViewPrice.setText(String.format("$%.2f", product.getPrice()));
            textViewQuantity.setText(String.valueOf(product.getQuantity()));

            // Retrieve category name and display it
            String categoryName = categoryDatabase.getCategoryNameById(product.getCategoryID());
            textViewCategory.setText(categoryName);

//            // Load product image using Glide
//            Glide.with(this)
//                    .load(product.getImage()) // URL or resource ID
//                    .into(imageView);

            if (product.getImage() != null) {
                Glide.with(this)
                        .load(product.getImage()) // URL or URI
                        .error(R.drawable.error_icon)
                        .into(imageView);
            } else {
                // Nếu không có hình ảnh, có thể đặt hình ảnh mặc định hoặc ẩn ImageView
                imageView.setImageResource(R.drawable.hide_image_icon); // Thay thế bằng hình ảnh tạm thời
            }
        }

        buttonDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(ProductDetail.this)
                    .setTitle("Delete Product")
                    .setMessage("Are you sure you want to delete this product?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        productDatabase.deleteProduct(productID);
                        // Set result and finish the activity
                        setResult(RESULT_OK);
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        buttonBack.setOnClickListener(v -> finish());

        buttonUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(ProductDetail.this, ProductUpdate.class);
            intent.putExtra("product_id", productID);
            startActivityForResult(intent, 1);
//            startActivity(intent);
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
            // Cập nhật giao diện người dùng với thông tin sản phẩm mới
            Product updatedProduct = (Product) data.getSerializableExtra("updated_product");
            if (updatedProduct != null) {
                textViewName.setText(updatedProduct.getName());
                textViewPrice.setText(String.format("$%.2f", updatedProduct.getPrice()));
                textViewQuantity.setText(String.valueOf(updatedProduct.getQuantity()));
                String categoryName = categoryDatabase.getCategoryNameById(updatedProduct.getCategoryID());
                textViewCategory.setText(categoryName);
                if (updatedProduct.getImage() != null) {
                    Glide.with(this).load(updatedProduct.getImage()).into(imageView);
                }
            }
        }
    }

}