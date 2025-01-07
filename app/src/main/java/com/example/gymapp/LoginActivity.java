//package com.example.gymapp;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.gymapp.KhachHang.KhachHang;
//
//public class LoginActivity extends AppCompatActivity {
//
//    private View viewLogin;
//    private View viewRegister;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_login);
//
//        viewLogin = findViewById(R.id.viewLogin);
//        viewRegister = findViewById(R.id.viewRegister);
//
//        TextView textViewLogin = findViewById(R.id.textViewLogin);
//        TextView textViewRegister = findViewById(R.id.textViewRegister);
//        TextView textViewLogin1 = findViewById(R.id.textViewLogin1);
//        TextView textViewRegister1 = findViewById(R.id.textViewRegister1);
//        Button loginButton = findViewById(R.id.login_button);
//        Button registerButton = findViewById(R.id.register_button);
//        Button cancelButton = findViewById(R.id.huy_button);
//
//        textViewLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showLoginView();
//            }
//        });
//
//        textViewRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showRegisterView();
//            }
//        });
//
//        textViewLogin1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showLoginView();
//            }
//        });
//
//        textViewRegister1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showRegisterView();
//            }
//        });
//
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Chuyển đến Activity mới sau khi nhấp vào nút đăng nhập
//                Intent intent = new Intent(LoginActivity.this, KhachHang.class);
//                startActivity(intent);
//            }
//        });
//
//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Có thể thực hiện hành động khác khi nhấp vào nút đăng ký
//                // Ví dụ, chuyển đến trang khác hoặc hiển thị thông báo
//                Intent intent = new Intent(LoginActivity.this, KhachHang.class);
//                startActivity(intent);
//            }
//        });
//
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Xử lý sự kiện khi nhấp vào nút hủy
//                // Ví dụ, quay lại màn hình đăng nhập
//                showLoginView();
//            }
//        });
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//
//    private void showLoginView() {
//        viewLogin.setVisibility(View.VISIBLE);
//        viewRegister.setVisibility(View.GONE);
//    }
//
//    private void showRegisterView() {
//        viewLogin.setVisibility(View.GONE);
//        viewRegister.setVisibility(View.VISIBLE);
//    }
//}



package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymapp.KhachHang.KhachHang;

public class LoginActivity extends AppCompatActivity {

    private View viewLogin;
    private View viewRegister;

    private EditText usernameLogin;
    private EditText passwordLogin;
    private EditText usernameRegister;
    private EditText passwordRegister;
    private EditText confirmPasswordRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        viewLogin = findViewById(R.id.viewLogin);
        viewRegister = findViewById(R.id.viewRegister);

        TextView textViewLogin = findViewById(R.id.textViewLogin);
        TextView textViewRegister = findViewById(R.id.textViewRegister);
        TextView textViewLogin1 = findViewById(R.id.textViewLogin1);
        TextView textViewRegister1 = findViewById(R.id.textViewRegister1);
        Button loginButton = findViewById(R.id.login_button);
        Button registerButton = findViewById(R.id.register_button);
        Button cancelButton = findViewById(R.id.huy_button);

        // Initialize EditTexts
        usernameLogin = findViewById(R.id.usernameLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        usernameRegister = findViewById(R.id.usernameRegister);
        passwordRegister = findViewById(R.id.passwordRegister);
//        confirmPasswordRegister = findViewById(R.id.confirm_password_register);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginView();
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterView();
            }
        });

        textViewLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginView();
            }
        });

        textViewRegister1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterView();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateLoginFields()) {
                    // Chuyển đến Activity mới sau khi nhấp vào nút đăng nhập
                    Intent intent = new Intent(LoginActivity.this, KhachHang.class);
                    startActivity(intent);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateRegisterFields()) {
                    // Chuyển đến Activity mới sau khi nhấp vào nút đăng ký
                    Intent intent = new Intent(LoginActivity.this, KhachHang.class);
                    startActivity(intent);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi nhấp vào nút hủy
                // Ví dụ, quay lại màn hình đăng nhập
                showLoginView();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showLoginView() {
        viewLogin.setVisibility(View.VISIBLE);
        viewRegister.setVisibility(View.GONE);
    }

    private void showRegisterView() {
        viewLogin.setVisibility(View.GONE);
        viewRegister.setVisibility(View.VISIBLE);
    }

    private boolean validateLoginFields() {
        String username = usernameLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validateRegisterFields() {
        String username = usernameRegister.getText().toString().trim();
        String password = passwordRegister.getText().toString().trim();
        String confirmPassword = confirmPasswordRegister.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (confirmPassword.isEmpty()) {
            Toast.makeText(this, "Confirm password is required", Toast.LENGTH_SHORT).show();
            return false;
        }

//        if (!password.equals(confirmPassword)) {
//            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        return true;
    }
}
