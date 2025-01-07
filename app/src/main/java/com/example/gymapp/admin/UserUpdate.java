package com.example.gymapp.admin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymapp.R;
import com.example.gymapp.databases.UserDatabase;
import com.example.gymapp.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UserUpdate extends AppCompatActivity {

    private UserDatabase userDatabase;
    private int userId;
    private EditText editTextUsername;
    private EditText editTextFullname;
    private EditText editTextRole;
    private EditText editTextEmail;
    private EditText editTextBirthday;
    private EditText editTextGender;
    private Button buttonSave;
    private Button buttonCancel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_update);

        userDatabase = new UserDatabase(this);
        userId = getIntent().getIntExtra("user_id", -1);

        // Find views by ID
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextFullname = findViewById(R.id.editTextFullname);
        editTextRole = findViewById(R.id.editTextRole);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextBirthday = findViewById(R.id.editTextBirthday);
        editTextGender = findViewById(R.id.editTextGender);
        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonCancel);

        // Load user data
        User user = userDatabase.layToanBoNguoiDung().stream().filter(u -> u.getId() == userId).findFirst().orElse(null);

        if (user != null) {
            editTextUsername.setText(user.getUsername());
            editTextFullname.setText(user.getFullname());
            editTextRole.setText(user.getRole());
            editTextEmail.setText(user.getEmail());
            editTextBirthday.setText(dateFormat.format(user.getBirthday()));
            editTextGender.setText(user.getGender());
        }

        // Set DatePicker for birthday
        editTextBirthday.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(UserUpdate.this, (view, year, month, dayOfMonth) -> {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);
                editTextBirthday.setText(dateFormat.format(selectedDate.getTime()));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Save updated user information
        buttonSave.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            String fullname = editTextFullname.getText().toString();
            String role = editTextRole.getText().toString();
            String email = editTextEmail.getText().toString();
            Date birthday;
            try {
                birthday = dateFormat.parse(editTextBirthday.getText().toString());
            } catch (Exception e) {
                birthday = new Date();
            }
            String gender = editTextGender.getText().toString();

            boolean updated = userDatabase.capNhatNguoiDung(userId, username, fullname, role, email, dateFormat.format(birthday), gender);
            if (updated) {
                Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close this activity and return to the previous one
            } else {
                Toast.makeText(this, "Failed to update user", Toast.LENGTH_SHORT).show();
            }
        });

        buttonCancel.setOnClickListener(v -> {
            finish(); // Close this activity and return to the previous one
        });

    }

}
