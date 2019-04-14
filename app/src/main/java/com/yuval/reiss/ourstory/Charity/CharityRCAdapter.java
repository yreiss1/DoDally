package com.yuval.reiss.ourstory.Charity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.yuval.reiss.ourstory.Charity.CharityViewHolder;
import com.yuval.reiss.ourstory.Objects.CharityObject;
import com.yuval.reiss.ourstory.R;

import java.util.ArrayList;

public class CharityRCAdapter extends RecyclerView.Adapter<CharityViewHolder>{


    private ArrayList<CharityObject> charityArrayList;
    private Context context;
    OnItemClickListener mClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public CharityRCAdapter(ArrayList<CharityObject> charityArrayList, Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.charityArrayList = charityArrayList;
        mClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CharityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_charity_item, viewGroup, false);
        CharityViewHolder cvh = new CharityViewHolder(layoutView);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CharityViewHolder charityViewHolder, final int i) {

        CharityObject charity = charityArrayList.get(i);
        String image = charity.getImageURL();
        charityViewHolder.name.setText(charity.getName());
        charityViewHolder.description.setText(charity.getDescription());
        Glide.with(context).load(charity.getImageURL()).into(charityViewHolder.image);

        charityViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return charityArrayList.size();
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
