package com.example.gymapp.admin;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.Manifest;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gymapp.R;
import com.example.gymapp.databases.CategoryDatabase;
import com.example.gymapp.databases.ProductDatabase;
import com.example.gymapp.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.util.List;

public class ProductUpdate extends AppCompatActivity {

    private ProductDatabase productDatabase;
    private CategoryDatabase categoryDatabase;
    private int productID;


    private EditText editTextName;
    private EditText editTextPrice;
    private EditText editTextQuantity;
    private Spinner spinnerCategory;
    private Button buttonSave;
    private Button buttonCancel;
//    private FloatingActionButton buttonUpload;
    private Button buttonUpload;

    private Uri imageUri;
    private ImageView imageViewProduct;
    private static final int REQUEST_CODE_PERMISSION = 100;
    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_product_update);

        productDatabase = new ProductDatabase(this);
        categoryDatabase = new CategoryDatabase(this);
        productID = getIntent().getIntExtra("product_id", -1);

        loadCategories();

        editTextName = findViewById(R.id.editTextName);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextQuantity = findViewById(R.id.editTextQuantity);


        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonCancel);
        buttonUpload = findViewById(R.id.buttonUpload);
        imageViewProduct = findViewById(R.id.imageViewProduct);
        registerResult();

        buttonSave.setOnClickListener(v -> saveProduct());
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        buttonUpload.setOnClickListener(view -> pickImage());


        Product product = productDatabase.getAllProducts().stream().filter(p -> p.getId() == productID).findFirst().orElse(null);

        if (product != null) {
            editTextName.setText(product.getName());
            editTextPrice.setText(String.valueOf((int) product.getPrice())); // Ensure price is set correctly
            editTextQuantity.setText(String.valueOf(product.getQuantity())); // Ensure quantity is set correctly

        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void saveProduct() {
        String name = editTextName.getText().toString();
        String priceStr = editTextPrice.getText().toString();
        String quantityStr = editTextQuantity.getText().toString();
        String categoryName = (String) spinnerCategory.getSelectedItem();

        // Chuyển đổi tên danh mục thành ID
        int categoryId = categoryDatabase.getCategoryIdByName(categoryName);

        double price = Double.parseDouble(priceStr);
        int quantity = Integer.parseInt(quantityStr);

        // Tạo hoặc cập nhật đối tượng Product
        Product product = new Product();
        product.setId(productID);
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setCategoryID(categoryId);
        product.setImage(imageUri != null ? imageUri.toString() : null);

        // Cập nhật sản phẩm vào cơ sở dữ liệu
        int rowsAffected = productDatabase.updateProduct(product);

        if (rowsAffected > 0) {
            // Thông báo thành công
            Toast.makeText(ProductUpdate.this, "Product updated successfully!", Toast.LENGTH_SHORT).show();
        } else {
            // Thông báo thất bại
            Toast.makeText(ProductUpdate.this, "Failed to update product.", Toast.LENGTH_SHORT).show();
        }

        // Trả về kết quả và kết thúc hoạt động
        Intent resultIntent = new Intent();
        resultIntent.putExtra("updated_product", String.valueOf(product));
        setResult(RESULT_OK, resultIntent);
        finish();
    }


    private void loadCategories() {
        List<String> categories = categoryDatabase.getAllCategoryNames(); // Giả sử phương thức này trả về danh sách tên danh mục
        spinnerCategory = findViewById(R.id.spinnerCategory);
        if (spinnerCategory != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(adapter);
        } else {
            Log.e("ProductUpdate", "spinnerCategory is null");
        }
    }

    private void registerResult(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try{
                            imageUri = result.getData().getData();
                            imageViewProduct.setImageURI(imageUri);
                        }catch (Exception e){
                            Toast.makeText(ProductUpdate.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void pickImage(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }
}