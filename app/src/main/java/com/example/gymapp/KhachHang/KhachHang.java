package com.example.gymapp.KhachHang;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymapp.R;

public class KhachHang extends AppCompatActivity {

    private ViewSwitcher viewSwitcher;
    private LinearLayout viewCuaHang;
    private LinearLayout viewCaNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_khach_hang);

        viewSwitcher = findViewById(R.id.viewSwitcher);
        viewCuaHang = findViewById(R.id.viewCuaHang);
        viewCaNhan = findViewById(R.id.viewCaNhan);


        viewCuaHang.setOnClickListener(v -> {
            viewSwitcher.setDisplayedChild(0);
        });

        viewCaNhan.setOnClickListener(v -> {
            viewSwitcher.setDisplayedChild(1);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewCuaHang.performClick();
    }
}