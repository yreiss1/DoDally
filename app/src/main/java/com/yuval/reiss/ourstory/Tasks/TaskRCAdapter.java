package com.yuval.reiss.ourstory.Tasks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.yuval.reiss.ourstory.R;
import com.yuval.reiss.ourstory.Utils.ItemTouchHelperAdapter;
import com.yuval.reiss.ourstory.Objects.TaskObject;
import com.yuval.reiss.ourstory.Utils.SendNotification;
import com.yuval.reiss.ourstory.Utils.UserInformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TaskRCAdapter extends RecyclerView.Adapter<TaskViewHolder> implements ItemTouchHelperAdapter {

    private Context context;
    ArrayList<TaskObject> taskArrayList;
    public TaskRCAdapter(ArrayList<TaskObject> taskArrayList, Context context) {
        this.taskArrayList = taskArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_task_item, viewGroup, false);
        TaskViewHolder svh = new TaskViewHolder(layoutView);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder mainViewHolder, int i) {
        final TaskObject task = taskArrayList.get(i);


        mainViewHolder.mTaskName.setText(task.getTitle().toUpperCase());
        mainViewHolder.mTime.setText(new SimpleDateFormat("MM/dd/yyyy").format(task.getTime()));
        mainViewHolder.mMoney.setText("$"+task.getAmount());
        mainViewHolder.mCause.setText("$ goes to " + task.getCause());

    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }

    @Override
    public void onItemDismiss(int position) {
        TaskObject taskObject = taskArrayList.remove(position);
        Toast.makeText(context, "Congrats on completing "+ taskObject.getTitle() + "!", Toast.LENGTH_LONG).show();
        new SendNotification("Good job on completing " + taskObject.getTitle(),"Good Job!", UserInformation.getNotify(),context);
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("tasks").child(taskObject.getUid()).setValue(null);
        notifyItemRemoved(position);

    }
}
