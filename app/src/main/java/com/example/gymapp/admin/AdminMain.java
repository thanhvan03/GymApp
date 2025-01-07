package com.example.gymapp.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymapp.DatabaseVersionChecker;
import com.example.gymapp.R;

public class AdminMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        int dbVersion = DatabaseVersionChecker.getDatabaseVersion(this);
//
//        TextView versionTextView = findViewById(R.id.titleText);
//        versionTextView.setText("Database Version: " + dbVersion);


        findViewById(R.id.cardViewUser).setOnClickListener(v -> {
            Intent intent = new Intent(AdminMain.this, UserManagement.class);
            startActivity(intent);
        });

        findViewById(R.id.cardViewCategory).setOnClickListener(v -> {
            Intent intent = new Intent(AdminMain.this, CategoryManagement.class);
            startActivity(intent);
        });

        findViewById(R.id.cardViewProduct).setOnClickListener(v -> {
            Intent intent = new Intent(AdminMain.this, ProductManagement.class);
            startActivity(intent);
        });

        findViewById(R.id.cardViewOrder).setOnClickListener(v -> {
            Intent intent = new Intent(AdminMain.this, OrderManagement.class);
            startActivity(intent);
        });

    }

}