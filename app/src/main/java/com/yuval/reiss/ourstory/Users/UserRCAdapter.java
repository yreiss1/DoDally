package com.yuval.reiss.ourstory.Users;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yuval.reiss.ourstory.Objects.UserObject;
import com.yuval.reiss.ourstory.R;
import com.yuval.reiss.ourstory.Utils.SendNotification;
import com.yuval.reiss.ourstory.Utils.UserInformation;

import java.util.ArrayList;

public class UserRCAdapter extends RecyclerView.Adapter<UserViewHolder> {


    private ArrayList<UserObject> userArrayList;
    private Context context;
    public UserRCAdapter(ArrayList<UserObject> usersArrayList, Context context) {
        this.userArrayList = usersArrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_user_item, viewGroup, false);
        UserViewHolder uvh = new UserViewHolder(view);
        return uvh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        final UserObject user = userArrayList.get(i);

        userViewHolder.mUserTextView.setText(user.getUsername());
        userViewHolder.mEmailTextView.setText(user.getEmail());

        int num = (i + 2)%10;
        String url = "http://lorempixel.com/400/200/animals/" + num;
        Glide.with(context).load(url).into(userViewHolder.mUserImageView);

        userViewHolder.mCheerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = UserInformation.getUsername() + " has cheered you on! You can do it!";
                new SendNotification(message, "You got this!", user.getNotify_id(), context);
                Toast.makeText(context, "You've cheered on " + user.getUsername(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }
}
