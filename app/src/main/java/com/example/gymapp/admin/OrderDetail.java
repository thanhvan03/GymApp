package com.example.gymapp.admin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymapp.R;
import com.example.gymapp.adapter.OrderDetailsAdapter;
import com.example.gymapp.databases.OrderDatabase;
import com.example.gymapp.databases.UserDatabase;
import com.example.gymapp.model.Order;
import com.example.gymapp.model.Product;

import java.util.List;

public class OrderDetail extends AppCompatActivity {

    private OrderDatabase orderDatabase;
    private UserDatabase userDatabase;

    private ListView listViewProducts;
    private List<Product> productList;
    private TextView textViewOrderID;
    private TextView textViewCustomerName;
    private EditText editTextTotal;
//    private EditText editTextStatus;
    private Spinner spinnerStatus;

    private Button buttonDelete;
    private Button buttonUpdate;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_order_details);

        orderDatabase = new OrderDatabase(this);
        userDatabase = new UserDatabase(this);

        textViewOrderID = findViewById(R.id.textViewOrderID);
        textViewCustomerName = findViewById(R.id.textViewCustomerName);
        listViewProducts = findViewById(R.id.listViewProducts);
        editTextTotal = findViewById(R.id.textViewTotal);
//        editTextStatus = findViewById(R.id.editTextStatus);
        spinnerStatus = findViewById(R.id.spinnerStatus);

        buttonBack = findViewById(R.id.buttonBack);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        ArrayAdapter<CharSequence> arrAdapter = ArrayAdapter.createFromResource(this,
                R.array.order_status_array, android.R.layout.simple_spinner_item);
        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(arrAdapter);

        int orderId = getIntent().getIntExtra("order_id", -1);

        if (orderId != -1) {
            // Fetch order details
            orderDatabase.open();
            Order order = orderDatabase.getOrderById(orderId);
            if (order != null) {
                // Update UI with order details
                textViewOrderID.setText(String.valueOf(order.getOrderID()));
                textViewCustomerName.setText(getUserFullName(order.getUserID()));
                editTextTotal.setText(String.valueOf(order.getTotalAmount()));
//                editTextStatus.setText(order.getStatus());
                spinnerStatus.setSelection(arrAdapter.getPosition(order.getStatus()));

                productList = orderDatabase.getProductsByOrderId(orderId);
                OrderDetailsAdapter adapter = new OrderDetailsAdapter(this, productList);
                listViewProducts.setAdapter(adapter);
            }
        }

        buttonBack.setOnClickListener(v -> finish());

        buttonDelete.setOnClickListener(v -> {
            if (orderId != -1) {
                orderDatabase.open();
                boolean success = orderDatabase.deleteOrderById(orderId);
                orderDatabase.close();
                if (success) {
                    Toast.makeText(this, "Order deleted successfully", Toast.LENGTH_SHORT).show();

                    // Set result and finish the activity
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(this, "Failed to delete order", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonUpdate.setOnClickListener(v -> {
            // Lấy dữ liệu từ các trường EditText
            String totalAmountString = editTextTotal.getText().toString();
//            String status = editTextStatus.getText().toString();
            String status = spinnerStatus.getSelectedItem().toString();

            if (totalAmountString.isEmpty() || status.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            double totalAmount = Double.parseDouble(totalAmountString);

            // Cập nhật đơn hàng trong cơ sở dữ liệu
            boolean isOrderUpdated = orderDatabase.updateOrder(orderId, totalAmount, status);
            if (!isOrderUpdated) {
                Toast.makeText(this, "Cập nhật đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lấy danh sách sản phẩm đã cập nhật từ ListView
            OrderDetailsAdapter adapter = (OrderDetailsAdapter) listViewProducts.getAdapter();
            List<Product> updatedProductList = adapter.getProductList();

            // Cập nhật số lượng của từng sản phẩm trong cơ sở dữ liệu
            orderDatabase.open();
            for (Product product : updatedProductList) {
                int productId = product.getId();
                int updatedQuantity = product.getQuantity();

                boolean isOrderDetailUpdated = orderDatabase.updateOrderDetail(orderId, productId, updatedQuantity);
                if (!isOrderDetailUpdated) {
                    Toast.makeText(this, "Cập nhật số lượng sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    orderDatabase.close();
                    return;
                }
            }
            orderDatabase.close();

            // Thông báo cho người dùng và kết thúc Activity
            Toast.makeText(this, "Cập nhật đơn hàng thành công", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private String getUserFullName(int userId) {
        userDatabase.open();
        String fullname = userDatabase.getUserFullNameById(userId);
        userDatabase.close();
        return fullname != null ? fullname : "Unknown";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database connection
        if (orderDatabase != null) {
            orderDatabase.close();
        }
        if (userDatabase != null) {
            userDatabase.close();
        }
    }
}