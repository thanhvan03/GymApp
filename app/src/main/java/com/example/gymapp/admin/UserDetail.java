package com.example.gymapp.admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.gymapp.R;
import com.example.gymapp.databases.UserDatabase;
import com.example.gymapp.model.User;

public class UserDetail extends AppCompatActivity {

    private UserDatabase userDatabase;
    private int userId;
    private User user;

    private ImageView imageViewAvatar;
    private TextView textViewFullname;
    private TextView textViewUsername;
    private TextView textViewRole;
    private TextView textViewBirthday;
    private TextView textViewGender;
    private TextView textViewEmail;
    private Button buttonDelete;
    private Button buttonUpdate;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_details);

        // Initialize UserManager
        userDatabase = new UserDatabase(this);

        // Retrieve userId from Intent
        userId = getIntent().getIntExtra("user_id", -1);

        // Find views by ID
        imageViewAvatar = findViewById(R.id.imageViewAvatar);
        textViewFullname = findViewById(R.id.textViewFullname);
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewRole = findViewById(R.id.textViewRole);
        textViewBirthday = findViewById(R.id.textViewBirthday);
        textViewGender = findViewById(R.id.textViewGender);
        textViewEmail = findViewById(R.id.textViewEmail);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonBack = findViewById(R.id.buttonBack);

        // Load user data
        user = userDatabase.layToanBoNguoiDung().stream().filter(u -> u.getId() == userId).findFirst().orElse(null);

        if (user != null) {
            // Set user details
            textViewFullname.setText(user.getFullname());
            textViewUsername.setText(user.getUsername());
            textViewRole.setText(user.getRole());
            textViewBirthday.setText(user.getBirthday().toString()); // Format as needed
            textViewGender.setText(user.getGender());
            textViewEmail.setText(user.getEmail());

            // Load avatar image using Glide
            Glide.with(this)
                    .load(user.getAvatar())
                    .placeholder(R.drawable.default_avatar)
                    .into(imageViewAvatar);
        }

        // Set click listener for Delete button
        buttonDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(UserDetail.this)
                    .setTitle("Delete User")
                    .setMessage("Are you sure you want to delete this user?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        userDatabase.xoaNguoiDung(userId);
                        finish(); // Close this activity and return to the previous one
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        // Set click listener for Update button
        buttonUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(UserDetail.this, UserUpdate.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });

        // Set click listener for Back button
        buttonBack.setOnClickListener(v -> finish());
    }
}
