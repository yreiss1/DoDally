package com.yuval.reiss.ourstory.MyTasks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yuval.reiss.ourstory.Objects.CharityObject;
import com.yuval.reiss.ourstory.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddTaskActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<CharityObject> charityArrayList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        charityArrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        mRecyclerView = findViewById(R.id.add_task_recyclerview);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CharityRCAdapter(charityArrayList, getApplicationContext(), new CharityRCAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(AddTaskActivity.this, CreateTaskActivity.class);
                intent.putExtra("name", charityArrayList.get(position).getName());
                intent.putExtra("description", charityArrayList.get(position).getDescription());
                intent.putExtra("thumbImage", charityArrayList.get(position).getThumbImageURL());
                intent.putExtra("image", charityArrayList.get(position).getImageURL());
                startActivity(intent);
                finish();
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        new AsyncTask().execute();



    }


    public class AsyncTask extends android.os.AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog = new ProgressDialog(AddTaskActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("\tLoading Charities...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void ...params) {

            String url = "https://api.donorschoose.org/common/json_feed.html?APIKey=DONORSCHOOSE";
            StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject json = new JSONObject(response);
                        JSONArray charities = json.getJSONArray("proposals");

                        for (int i = 0; i < charities.length(); i++) {
                            JSONObject charityJSON = charities.getJSONObject(i);
                            String name = charityJSON.isNull("title")? "" : charityJSON.optString("title");
                            String description = charityJSON.isNull("shortDescription")? "" : charityJSON.optString("shortDescription");
                            String thumbImageURL = charityJSON.isNull("thumbImageURL")? "default" : charityJSON.optString("thumbImageURL");
                            String imageURL = charityJSON.isNull("imageURL")?"default" : charityJSON.optString("imageURL");


                            CharityObject charity = new CharityObject(name, description,thumbImageURL,imageURL);
                            charityArrayList.add(charity);
                            mAdapter.notifyDataSetChanged();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddTaskActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });


            requestQueue.add(req);
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

    }

}
