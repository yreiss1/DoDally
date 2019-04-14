package com.yuval.reiss.ourstory.Tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;
import com.yuval.reiss.ourstory.Charity.CharityActivity;
import com.yuval.reiss.ourstory.LoginActivity;
import com.yuval.reiss.ourstory.MainActivity;
import com.yuval.reiss.ourstory.Objects.TaskObject;
import com.yuval.reiss.ourstory.R;

import java.util.ArrayList;
import java.util.Date;

public class TasksFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TaskRCAdapter mAdapter;
    private ArrayList<TaskObject> myTasksArrayList;
    private FloatingActionButton fab;
    SwipeRefreshLayout mSwipeRefreshLayout;


    public static TasksFragment newInstance() {
        return new TasksFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        myTasksArrayList = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.mine_recyclerview);
        fab = view.findViewById(R.id.mine_fab);
        mRecyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new TaskRCAdapter(myTasksArrayList, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout = view.findViewById(R.id.tasks_swipe_refresh);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CharityActivity.class);
                startActivity(intent);
                return;
            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                OneSignal.setSubscription(false);
                Toast.makeText(getContext(), "See you later!", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myTasksArrayList.clear();
                mSwipeRefreshLayout.setRefreshing(false);
                showTasks();
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT | ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                //Remove swiped item from list and notify the RecyclerView
                mAdapter.onItemDismiss(viewHolder.getAdapterPosition());

            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        showTasks();

        return view;




    }

    public void showTasks() {

        Query query = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("tasks");
        query.orderByChild("time").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    TaskObject task = data.getValue(TaskObject.class);
                    task.setUid(data.getKey());
                    myTasksArrayList.add(task);
                }

                mAdapter.notifyDataSetChanged();
                if (!myTasksArrayList.isEmpty()) {
                    ((MainActivity) getActivity()).countDownStart(new Date(myTasksArrayList.get(0).getTime()));
                } else {
                    ((MainActivity) getActivity()).countDownStart(new Date());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
