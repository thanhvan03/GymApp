package com.example.gymapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gymapp.R;
import com.example.gymapp.model.Product;

import java.util.List;

public class OrderDetailsAdapter extends ArrayAdapter<Product> {

    private Context context;
    private List<Product> products;

    public OrderDetailsAdapter(Context context, List<Product> products) {
        super(context, R.layout.products_in_order_item, products);
        this.context = context;
        this.products = products;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.products_in_order_item, parent, false);
        }

        // Get the data item for this position
        Product product = getItem(position);

        // Lookup view for data population
        TextView productName = convertView.findViewById(R.id.productName);
        TextView productPrice = convertView.findViewById(R.id.productPrice);
        EditText productQuantity = convertView.findViewById(R.id.productQuantity);

        // Populate the data into the template view using the data object
        if (product != null) {
            productName.setText(product.getName());
            productPrice.setText(String.format("Giá: $%.2f", product.getPrice()));
            productQuantity.setText(String.format("Số lượng: %d", product.getQuantity()));
        }

        // Return the completed view to render on screen
        return convertView;
    }
    public List<Product> getProductList() {
        return products;
    }
}
