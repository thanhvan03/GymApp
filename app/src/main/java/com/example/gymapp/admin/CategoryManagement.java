package com.example.gymapp.admin;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymapp.R;
import com.example.gymapp.adapter.CategoryAdapter;
import com.example.gymapp.databases.CategoryDatabase;
import com.example.gymapp.model.Category;

import java.util.List;


public class CategoryManagement extends AppCompatActivity {

    private ListView listViewCategories;
    private CategoryDatabase categoryDatabase;
    private List<Category> categoryList;
    private CategoryAdapter categoryAdapter;
    private Button buttonReturn;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_category_management);

        categoryDatabase = new CategoryDatabase(this);

        listViewCategories = findViewById(R.id.listViewCategories);

        categoryList = categoryDatabase.getAllCategories();
        categoryAdapter = new CategoryAdapter(this, categoryList);
        listViewCategories.setAdapter(categoryAdapter);

        buttonReturn = findViewById(R.id.buttonReturn);
        buttonAdd = findViewById(R.id.buttonAdd);

        buttonReturn.setOnClickListener(v -> {
            // Finish the current activity to return to the previous one
            finish();
        });

        buttonAdd.setOnClickListener(v -> showAddCategoryDialog());

        updateCategoryList();

        listViewCategories.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = categoryList.get(position);
                showCategoryDetailsDialog(selectedCategory);
                return true;
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void updateCategoryList() {
        categoryList = categoryDatabase.getAllCategories();
        categoryAdapter = new CategoryAdapter(this, categoryList);
        listViewCategories.setAdapter(categoryAdapter);
    }

    private void showCategoryDetailsDialog(final Category category) {
        // Inflate the dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_category_details, null);

        // Find views
        TextView textViewCategoryID = dialogView.findViewById(R.id.textViewCategoryID);
        TextView textViewCategoryName = dialogView.findViewById(R.id.textViewCategoryName);
        Button buttonDelete = dialogView.findViewById(R.id.buttonDelete);
        Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdate);
        Button buttonReturn = dialogView.findViewById(R.id.buttonReturn);

        // Set product details
        textViewCategoryID.setText("Mã danh mục: " + category.getCategoryID());
        textViewCategoryName.setText("Tên danh mục: " + category.getCategoryName());

        // Create and show the dialog
        final Dialog dialog = new Dialog(CategoryManagement.this);
        dialog.setContentView(dialogView);
        dialog.show();

        // Handle delete button click
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rowsAffected = categoryDatabase.deleteCategory(category.getCategoryID());
                if (rowsAffected > 0) {
                    Toast.makeText(CategoryManagement.this, "Xóa danh mục thành công!", Toast.LENGTH_SHORT).show();
                    updateCategoryList();
                    dialog.dismiss();
                } else {
                    Toast.makeText(CategoryManagement.this, "Lỗi khi xóa danh mục!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Handle update button click
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateCategoryDialog(category, dialog);
            }
        });

        // Handle return button click
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void showUpdateCategoryDialog(final Category category, final Dialog parentDialog) {
        // Inflate the update dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View updateView = inflater.inflate(R.layout.dialog_category_update, null);

        // Find views
        final EditText editTextCategoryName = updateView.findViewById(R.id.editTextCategoryName);
        Button buttonCapNhat = updateView.findViewById(R.id.buttonCapNhat);
        Button buttonHuy = updateView.findViewById(R.id.buttonHuy);

        // Set current product details
        editTextCategoryName.setText(String.valueOf(category.getCategoryName()));

        // Create and show the update dialog
        final Dialog updateDialog = new Dialog(CategoryManagement.this);
        updateDialog.setContentView(updateView);
        updateDialog.show();

        // Handle update button click
        buttonCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = editTextCategoryName.getText().toString();

                int rowsAffected = categoryDatabase.updateCategory(category.getCategoryID(),categoryName);
                if (rowsAffected > 0) {

                    Toast.makeText(CategoryManagement.this, "Cập nhật danh mục thành công!", Toast.LENGTH_SHORT).show();
                    updateCategoryList();
                    updateDialog.dismiss();
                    parentDialog.dismiss();
                } else {
                    Toast.makeText(CategoryManagement.this, "Lỗi khi cập nhật danh mục!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Handle cancel button click
        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog.dismiss();
            }
        });
    }

    private void showAddCategoryDialog() {
        // Inflate the add dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View addView = inflater.inflate(R.layout.dialog_category_add, null);

        // Find views
        final EditText editTextCategoryName = addView.findViewById(R.id.editTextCategoryName);
        Button buttonAdd = addView.findViewById(R.id.buttonAdd);
        Button buttonCancel = addView.findViewById(R.id.buttonCancel);

        // Create and show the dialog
        final Dialog addDialog = new Dialog(CategoryManagement.this);
        addDialog.setContentView(addView);
        addDialog.show();

        // Handle add button click
        buttonAdd.setOnClickListener(v -> {
            String categoryName = editTextCategoryName.getText().toString();

            if (categoryName.isEmpty()) {
                Toast.makeText(CategoryManagement.this, "Tên danh mục không được để trống!", Toast.LENGTH_SHORT).show();
                return;
            }

            Category newCategory = new Category(); // categoryID sẽ được tự động gán trong cơ sở dữ liệu
            newCategory.setCategoryName(categoryName);

            long newCategoryId = categoryDatabase.addCategory(newCategory);
            if (newCategoryId != -1) {
                Toast.makeText(CategoryManagement.this, "Thêm danh mục thành công!", Toast.LENGTH_SHORT).show();
                updateCategoryList(); // Cập nhật danh sách danh mục
                addDialog.dismiss();
            } else {
                Toast.makeText(CategoryManagement.this, "Lỗi khi thêm danh mục!", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle cancel button click
        buttonCancel.setOnClickListener(v -> addDialog.dismiss());
    }

}