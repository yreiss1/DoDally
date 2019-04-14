package com.yuval.reiss.ourstory.Charity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuval.reiss.ourstory.R;

public class CharityViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView name;
    public TextView description;
    public RelativeLayout charityLayout;
    public CharityViewHolder.ClickListener mClickListener;

    public CharityViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.charity_item_imageview);
        name = itemView.findViewById(R.id.charity_name_textview);
        description = itemView.findViewById(R.id.charity_description);
        charityLayout = itemView.findViewById(R.id.charity_item_layout);

    }

    public interface ClickListener {
        public void onItemClick(View view, int position);
    }
}
