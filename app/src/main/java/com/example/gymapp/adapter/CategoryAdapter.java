package com.example.gymapp.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gymapp.R;
import com.example.gymapp.model.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        super(context, 0, categoryList);
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        }

        Category category = categoryList.get(position);

        TextView textViewID = convertView.findViewById(R.id.categoryIdTextView);
        textViewID.setText(String.valueOf(category.getCategoryID()));

        TextView textViewName = convertView.findViewById(R.id.categoryNameTextView);
        textViewName.setText(category.getCategoryName());

        return convertView;
    }
}
