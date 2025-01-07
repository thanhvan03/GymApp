package com.example.gymapp.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymapp.R;
import com.example.gymapp.adapter.UserAdapter;
import com.example.gymapp.databases.UserDatabase;
import com.example.gymapp.model.User;

import java.util.List;

public class UserManagement extends AppCompatActivity {

    private ListView listViewUsers;
    private UserDatabase userDatabase;
    private UserAdapter userAdapter;
    private List<User> userList;
    private Button buttonReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_user_management);

        // Initialize UserManager
        userDatabase = new UserDatabase(this);

        // Find ListView by ID
        listViewUsers = findViewById(R.id.listViewUsers);

        buttonReturn = findViewById(R.id.buttonReturn);

        // Fetch all users from the database
        userList = userDatabase.layToanBoNguoiDung();

        // Create and set the adapter
        userAdapter = new UserAdapter(this, userList);
        listViewUsers.setAdapter(userAdapter);

        // Set long-click listener for ListView items
        listViewUsers.setOnItemLongClickListener((parent, view, position, id) -> {
            User selectedUser = userList.get(position);

            // Open DetailActivity with the selected user's information
            Intent intent = new Intent(UserManagement.this, UserDetail.class);
            intent.putExtra("user_id", selectedUser.getId());
            startActivity(intent);

            return true; // Indicates that the long-click was handled
        });

        buttonReturn.setOnClickListener(v -> {
            // Finish the current activity to return to the previous one
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh user list when coming back from UserDetailActivity
        userList.clear();
        userList.addAll(userDatabase.layToanBoNguoiDung());
        userAdapter.notifyDataSetChanged();
    }
}
