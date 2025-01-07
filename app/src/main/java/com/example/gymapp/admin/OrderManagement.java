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
import com.example.gymapp.adapter.OrderAdapter;
import com.example.gymapp.adapter.ProductAdapter;
import com.example.gymapp.databases.OrderDatabase;
import com.example.gymapp.databases.ProductDatabase;
import com.example.gymapp.model.Order;

import java.util.List;

public class OrderManagement extends AppCompatActivity {

    private ListView listViewOrders;
    private OrderDatabase orderDatabase;
    private List<Order> orderList;
    private OrderAdapter orderAdapter;

    private Button buttonReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_order_management);

        orderDatabase = new OrderDatabase(this);
        listViewOrders = findViewById(R.id.listViewOrders);

        buttonReturn = findViewById(R.id.buttonReturn);

        orderList = orderDatabase.getAllOrders();
        orderAdapter = new OrderAdapter(this, orderList);
        listViewOrders.setAdapter(orderAdapter);

        listViewOrders.setOnItemLongClickListener((parent, view, position, id) -> {
            Order selectedOrder = orderList.get(position);

            // Open DetailActivity with the selected user's information
            Intent intent = new Intent(OrderManagement.this, OrderDetail.class);
            intent.putExtra("order_id", selectedOrder.getOrderID());
//            startActivity(intent);
            startActivityForResult(intent, 1);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Refresh the order list and update the adapter
            orderList = orderDatabase.getAllOrders();
            orderAdapter = new OrderAdapter(this, orderList);
            listViewOrders.setAdapter(orderAdapter);
        }
    }
}