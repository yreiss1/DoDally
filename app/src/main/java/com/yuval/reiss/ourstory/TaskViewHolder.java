package com.yuval.reiss.ourstory;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    CardView mCardView;
    public TextView mMoney;
    public TextView mTaskName;
    public TextView mTime;
    public TextView mCause;
    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);

        mMoney = itemView.findViewById(R.id.task_money_textview);
        mTaskName = itemView.findViewById(R.id.task_name_textview);
        mTime = itemView.findViewById(R.id.task_time_textview);
        mCause = itemView.findViewById(R.id.task_cause_textview);



    }
}
