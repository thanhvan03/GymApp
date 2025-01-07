package com.example.gymapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gymapp.R;
import com.example.gymapp.model.Order;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends ArrayAdapter<Order> {
    private Context context;
    private List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        super(context, 0, orderList);
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Nếu không có view tái sử dụng, tạo mới
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        }

        // Lấy đối tượng Order hiện tại
        Order order = getItem(position);

        // Ánh xạ các thành phần của layout
        TextView orderIDView = convertView.findViewById(R.id.orderIdTextView);
        TextView orderDate = convertView.findViewById(R.id.orderDate);
        TextView statusView = convertView.findViewById(R.id.orderStatus);

        // Đặt dữ liệu vào các thành phần của layout
        if (order != null) {
            orderIDView.setText("Order ID: " + order.getOrderID());
            statusView.setText("Status: " + order.getStatus());

            if (order.getOrderDate() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = dateFormat.format(order.getOrderDate());
                orderDate.setText("Order Date: " + formattedDate);
            } else {
                orderDate.setText("Order Date: Không xác định"); // Hoặc giá trị mặc định khác
            }
        }

        return convertView;
    }
}
