package com.yuval.reiss.ourstory.Users;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.yuval.reiss.ourstory.Objects.TaskObject;
import com.yuval.reiss.ourstory.Objects.UserObject;
import com.yuval.reiss.ourstory.R;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private UserRCAdapter mAdapter;
    private ArrayList<UserObject> usersArrayList;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static UserFragment newInstance() {
        return new UserFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        usersArrayList = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.saved_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new UserRCAdapter(usersArrayList, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout = view.findViewById(R.id.users_swipe_refresh);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                usersArrayList.clear();
                mSwipeRefreshLayout.setRefreshing(false);
                loadUsers();
            }
        });


        loadUsers();
        return view;
    }


    public void loadUsers() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Friends..");
        progressDialog.show();

        Query query = FirebaseDatabase.getInstance().getReference().child("users");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    UserObject user = data.getValue(UserObject.class);
                    usersArrayList.add(user);
                    mAdapter.notifyDataSetChanged();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}

