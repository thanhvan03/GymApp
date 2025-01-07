package com.example.gymapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gymapp.R;
import com.example.gymapp.model.User;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private Context context;
    private List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        super(context, 0, userList);
        this.context = context;
        this.userList = userList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        }

        User user = userList.get(position);

        TextView textViewFullname = convertView.findViewById(R.id.textViewFullname);
        textViewFullname.setText(user.getFullname());

        TextView textViewUsername = convertView.findViewById(R.id.textViewUsername);
        textViewUsername.setText(user.getUsername());

        ImageView imageViewAvatar = convertView.findViewById(R.id.imageViewAvatar);

        // Sử dụng Glide để tải và hiển thị hình ảnh avatar
        Glide.with(context)
                .load(user.getAvatar()) // Giả sử avatar là URL của hình ảnh
                .placeholder(R.drawable.default_avatar) // Hình ảnh mặc định khi đang tải
                .into(imageViewAvatar);

        return convertView;
    }
}
