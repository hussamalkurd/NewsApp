package com.example.myapplication.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.AddTaskActivity;
import com.example.myapplication.DetailActivity;
import com.example.myapplication.ExampleAdapter;
import com.example.myapplication.ExampleItem;
import com.example.myapplication.R;
import com.example.myapplication.app.AppController;
import com.example.myapplication.utill.APIURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment implements ExampleAdapter.OnItemClickListener, SearchView.OnQueryTextListener {
    Button button;
    ProgressDialog progressDialog;
    ArrayList<ExampleItem> data = new ArrayList<>();
    ExampleItem exampleItems;

    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String EXTRA_LIKES = "likeCount";
    public static final String EXTRA_SUMMERY = "mySummery";
    public static final String EXTRA_TIME = "myTime";
    public static final String EXTRA_DESCRIPTION = "myDescription";

    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;

    private RequestQueue mRequestQueue;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        // Inflate the layout for this fragment

        //  button =  view.findViewById(R.id.MyButton);
        // final FirstFragment context = this;

       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getContext(),AddTaskActivity.class);
                startActivity(myIntent);
            }
        });*/

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mExampleList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(getContext());
        parseJSON();
        return view;
    }

    private void parseJSON() {
        String url = "http://www.beststrends.com/tasks/api/index.php/getTasks";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("tasks");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String myDescription = hit.getString("description");
                                String mySummery = hit.getString("summary");
                                String creatorName = hit.getString("title");
                                String imageUrl = hit.getString("image");
                                String myTime = hit.getString("date");
                                int likeCount = hit.getInt("id");

                                mExampleList.add(new ExampleItem(imageUrl, creatorName, likeCount, mySummery, myTime,myDescription));
                            }

                            mExampleAdapter = new ExampleAdapter(getContext(), mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);
                            mExampleAdapter.setOnItemClickListener(FirstFragment.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        /*ntent intent = new Intent(getActivity(), TaskDetailsActivity.class);

                            intent.putExtra("task",new Task(tasks.get(i).getId(),tasks.get(i).getTitle(),tasks.get(i).getSummary()
                                    ,tasks.get(i).getDescription(),tasks.get(i).getDate(),tasks.get(i).getImage()
                                    ,tasks.get(i).getLongitude(),tasks.get(i).getLatitude()));

                            startActivity(intent);*/
        Intent detailIntent = new Intent(getContext(), DetailActivity.class);
        ExampleItem clickedItem = mExampleList.get(position);

       detailIntent.putExtra("exampleItem", (new ExampleItem(mExampleList.get(position).getCreator(),
                mExampleList.get(position).getmSummery(),
                mExampleList.get(position).getLikeCount(),
                mExampleList.get(position).getmTime(),
                mExampleList.get(position).getImageUrl(),
               mExampleList.get(position).getmDescription())));
       detailIntent.putExtra(EXTRA_DESCRIPTION,clickedItem.getmDescription());
       detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_CREATOR, clickedItem.getCreator());
        detailIntent.putExtra(EXTRA_LIKES, clickedItem.getLikeCount());
        detailIntent.putExtra(EXTRA_SUMMERY, clickedItem.getmSummery());
        detailIntent.putExtra(EXTRA_TIME, clickedItem.getmTime());


        startActivity(detailIntent);
    }

    public void showDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("البحث");

        super.onCreateOptionsMenu(menu, inflater);
    }
/*
@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("البحث");

        super.onCreateOptionsMenu(menu, inflater);
    }
Chat conversation end
Type a message...
* */
    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<ExampleItem> arrayList = new ArrayList<>();

        for(ExampleItem task : mExampleList){
            if(task.getCreator().toLowerCase().contains(newText.toLowerCase())){
                arrayList.add(task);
            }
        }

        mExampleAdapter = new ExampleAdapter(getActivity(),arrayList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mExampleAdapter);

        return true;
    }
}
