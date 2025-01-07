package com.example.gymapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gymapp.R;
import com.example.gymapp.model.Product;

import com.bumptech.glide.Glide; // Use Glide for image loading

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        super(context, 0, productList);
        this.context = context;
        this.productList = productList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_item, parent, false);
        }

        // Get the data item for this position
        Product product = getItem(position);

        // Lookup view for data population
//        ImageView imageView = convertView.findViewById(R.id.productImage);
        TextView id = convertView.findViewById(R.id.productID);
        TextView nameTextView = convertView.findViewById(R.id.productName);
        TextView priceTextView = convertView.findViewById(R.id.productPrice);

        // Populate the data into the template view using the data object
        id.setText("Mã sản phẩm: " + String.valueOf(product.getId()));
        nameTextView.setText(product.getName());
        priceTextView.setText(String.format("$%.2f", product.getPrice()));


//        String imageUrl = product.getImage(); // URL or resource ID
//        if (imageUrl != null && !imageUrl.isEmpty()) {
//            Glide.with(getContext())
//                    .load(imageUrl) // URL hoặc resource ID
//                    .error(R.drawable.error_icon) // Optional error image
//                    .into(imageView);
//        } else {
//            imageView.setImageResource(R.drawable.hide_image_icon); // Optional default image
//        }

        // Return the completed view to render on screen
        return convertView;
    }
}