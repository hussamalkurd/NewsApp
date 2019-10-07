package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.app.AppController;
import com.example.myapplication.utill.APIURL;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.myapplication.fragments.FirstFragment.EXTRA_CREATOR;
import static com.example.myapplication.fragments.FirstFragment.EXTRA_LIKES;
import static com.example.myapplication.fragments.FirstFragment.EXTRA_SUMMERY;
import static com.example.myapplication.fragments.FirstFragment.EXTRA_TIME;
import static com.example.myapplication.fragments.FirstFragment.EXTRA_URL;

public class DetailActivity extends AppCompatActivity {
    ExampleItem exampleItem;
    ProgressDialog progressDialog;
    MainActivity mainActivity;
    int id;
    TextView creatorName,likeCount,myTime,mySummery;
    private RequestQueue mRequestQueue;
    ArrayList<ExampleItem> data = new ArrayList<>();
    private ExampleAdapter mExampleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String creatorName = intent.getStringExtra(EXTRA_CREATOR);
        int likeCount = intent.getIntExtra(EXTRA_LIKES, 0);
        String mySummery = intent.getStringExtra(EXTRA_SUMMERY);
        String myTime = intent.getStringExtra(EXTRA_TIME);

        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewCreator = findViewById(R.id.text_view_creator_detail);
        TextView textViewLikes = findViewById(R.id.text_view_like_detail);
        TextView textViewSummery = findViewById(R.id.Suma);
        TextView textViewTime = findViewById(R.id.tim);
        TextView textViewDescription = findViewById(R.id.Description);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){

            exampleItem = (ExampleItem) getIntent().getSerializableExtra("exampleItem");


            getSupportActionBar().setTitle(exampleItem.getCreator());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Picasso.get().load(imageUrl).fit().centerInside().into(imageView);
        textViewCreator.setText(exampleItem.getCreator());
        textViewLikes.setText("رقم المهمة: " + exampleItem.getLikeCount());
        textViewTime.setText(exampleItem.getmTime());
        textViewSummery.setText(exampleItem.getmSummery());
        textViewDescription.setText(exampleItem.getmDescription());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_delet) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
            builder.setTitle("تأكيد الحذف");
            builder.setMessage("هل انت متأكد؟");


            builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();


                    deleteTask(exampleItem.getLikeCount());


                }
            });

            builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            builder.show();


        }

        return super.onOptionsItemSelected(item);
    }
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.delete_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_delet:

                showAlert();

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showAlert() {

        AlertDialog.Builder alertBilder = new AlertDialog.Builder(this);
        alertBilder.setTitle("تأكيد الحذف")
                .setMessage("هل انت متأكد؟")
                .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete contact
                      //  db.deletContact(id);
                  //deleteFromServer(id);

                        deleteTask(exampleItem.getLikeCount());
                        finish();
                    }
                })
                .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = alertBilder.create();
        dialog.show();
    }
*/
    public void deleteTask(final int id){
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIURL.URL_DELETE_API,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                Toast.makeText(DetailActivity.this, response, Toast.LENGTH_SHORT).show();
                hideDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
                hideDialog();


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("task_id",String.valueOf(id));
                return map;
            }
        };

        AppController.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }
    public void showDialog() {
        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setMessage("تحميل ....");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
   /* public void deleteFromServer(int id)
    {
        String url = "http://www.beststrends.com/tasks/api/index.php/deleteTask";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // if success

                        delete(getTaskId());
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("task_id","");

                return params;
            }
        };
        mRequestQueue.add(postRequest);
    }

    public void delete(int position) {

        try {
            data.remove(position);
            mExampleAdapter.notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }*/

}
