package com.yuval.reiss.ourstory.Users;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuval.reiss.ourstory.R;


public class UserViewHolder extends RecyclerView.ViewHolder {

    public ImageView mUserImageView;
    public TextView mUserTextView;
    public TextView mEmailTextView;
    public Button mCheerButton;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        mUserImageView = itemView.findViewById(R.id.user_imageview);
        mUserTextView = itemView.findViewById(R.id.username_textview);
        mEmailTextView = itemView.findViewById(R.id.user_email_textview);
        mCheerButton = itemView.findViewById(R.id.user_cheer_button);

    }
}
