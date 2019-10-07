package com.example.myapplication.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.DetailActivity;
import com.example.myapplication.ExampleAdapter;
import com.example.myapplication.ExampleItem;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeconedFragment extends Fragment implements ExampleAdapter.OnItemClickListener {

    ProgressDialog progressDialog;


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
    public SeconedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seconed, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mExampleList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(getContext());
        parseJSON();
        // Inflate the layout for this fragment
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
                            mExampleAdapter.setOnItemClickListener(SeconedFragment.this);

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
}
