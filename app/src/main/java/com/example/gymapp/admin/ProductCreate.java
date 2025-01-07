package com.example.gymapp.admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import com.example.gymapp.R;
import com.example.gymapp.databases.CategoryDatabase;
import com.example.gymapp.databases.ProductDatabase;
import com.example.gymapp.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.util.List;

public class ProductCreate extends AppCompatActivity {

    private ProductDatabase productDatabase;
    private CategoryDatabase categoryDatabase;
    private int productID;

    private TextView titleText;
    private EditText editTextName;
    private EditText editTextPrice;
    private EditText editTextQuantity;
    private Spinner spinnerCategory;
    private Button buttonSave;
    private Button buttonCancel;
    private Button buttonUpload;

    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUEST_PERMISSION = 2;
    private Uri imageUri;
    private ImageView imageViewProduct;
    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_product_create);

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
        buttonCancel.setOnClickListener(v -> finish());
        buttonUpload.setOnClickListener(view -> pickImage());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void saveProduct() {
        // Get user inputs
        String name = editTextName.getText().toString().trim();
        String priceStr = editTextPrice.getText().toString().trim();
        String quantityStr = editTextQuantity.getText().toString().trim();
        String category = (String) spinnerCategory.getSelectedItem();

        // Validate inputs
        if (name.isEmpty()) {
            Toast.makeText(this, "Product name is required", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        int quantity;
        if (priceStr.isEmpty() || quantityStr.isEmpty()) {
            Toast.makeText(this, "Price and quantity are required", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            price = Double.parseDouble(priceStr);
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price or quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        int categoryId = categoryDatabase.getCategoryIdByName(category);
        if (categoryId == -1) {
            Toast.makeText(this, "Invalid category", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Product object
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setCategoryID(categoryId);
        product.setImage(imageUri != null ? imageUri.toString() : null);
        // Handle image URI if available
//        if (imageUri != null) {
//            product.setImage(imageUri.toString());
//        }

        // Insert the product into the database
        long result = productDatabase.addProduct(product);
        if (result != -1) {
            Toast.makeText(this, "Product saved successfully", Toast.LENGTH_SHORT).show();
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(this, "Failed to save product", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadCategories() {
        List<String> categories = categoryDatabase.getAllCategoryNames(); // Giả sử phương thức này trả về danh sách tên danh mục
        spinnerCategory = findViewById(R.id.spinnerCategory);
        if (spinnerCategory != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(adapter);
        } else {
            Log.e("ProductCreate", "spinnerCategory is null");
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
                            Toast.makeText(ProductCreate.this, "No Image Selected", Toast.LENGTH_SHORT).show();
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