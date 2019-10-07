package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.example.myapplication.app.AppController;
import com.example.myapplication.utill.APIURL;

import java.util.HashMap;
import java.util.Map;

public class AddTaskActivity extends Activity {
    TextView tvIsConnected;
    Button button;
    EditText Title, Summery, Description, Status, Img;
    String server_url = "http://www.beststrends.com/tasks/api/index.php/addTask";
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;
    String title, summery, description, img, status;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);


        button = findViewById(R.id.btnSend);
        Title = findViewById(R.id.etTitle);
        Summery = findViewById(R.id.etSummery);
        Description = findViewById(R.id.etDescription);
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
        // Status = findViewById(R.id.etStatus);
        Img = findViewById(R.id.etMessage);
        builder = new AlertDialog.Builder(AddTaskActivity.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = Title.getText().toString();
                summery = Summery.getText().toString();
                description = Description.getText().toString();
                img = Img.getText().toString();

                if (Title.getText().toString().isEmpty()) {
                    Toast.makeText(AddTaskActivity.this, "الرجاء ادخل العنوان", Toast.LENGTH_SHORT).show();
                    return;

                } else if (Img.getText().toString().isEmpty()) {
                    Toast.makeText(AddTaskActivity.this, "الرجاء ادخال الصورة", Toast.LENGTH_SHORT).show();
                    return;

                } else if (Description.getText().toString().isEmpty()) {
                    Toast.makeText(AddTaskActivity.this, "الرجاء ادخال الوصف", Toast.LENGTH_SHORT).show();
                    return;


                } else if (Summery.getText().toString().isEmpty()) {
                    Toast.makeText(AddTaskActivity.this, "الرجاء ادخال الملخص", Toast.LENGTH_SHORT).show();
                    return;
                }

                Img.setText("");
                Title.setText("");
                Summery.setText("");
                Description.setText("");
                getStringRequest();
            }
        });
      /*  button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title, summery, description, img, status;
                title = Title.getText().toString();
                summery = Summery.getText().toString();
                description = Description.getText().toString();
                img = Img.getText().toString();

               /* StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,

                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                builder.setTitle("Server Response");
                                builder.setMessage("Response :" + response);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Img.setText("");
                                        Title.setText("");
                                        Summery.setText("");
                                        Description.setText("");
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(AddTaskActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("title", title);
                        params.put("summary", summery);
                        params.put("description", description);
                        params.put("image", img);

                        return super.getParams();
                    }
                };
                //AppController.getInstance(AddTaskActivity.this).addToRequestQueue(stringRequest);
            }
        });*/

        checkNetworkConnection();


    }


    public boolean checkNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean isConnected = false;
        if (networkInfo != null && (isConnected = networkInfo.isConnected())) {
            // show "Connected" & type of network "WIFI or MOBILE"
            tvIsConnected.setText("متصل بشبكة الواي فاي " + networkInfo.getTypeName());
            // change background color to red
            tvIsConnected.setBackgroundColor(0xFF7CCC26);


        } else {
            // show "Not Connected"
            tvIsConnected.setText("غير متصل");
            // change background color to green
            tvIsConnected.setBackgroundColor(0xFFFF0000);
        }

        return isConnected;
    }


    public void getStringRequest() {
        // final ImageView img = findViewById(R.id.img);

        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIURL.POST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("hzm", response);


                        hideDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("hzm", error.getMessage());
                        hideDialog();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("title", title);
                map.put("summary", summery);
                map.put("description", description);
                map.put("image", img);

                return map;
            }
        };

        AppController.getInstance(AddTaskActivity.this).addToRequestQueue(stringRequest);
    }

    public void showDialog() {
        progressDialog = new ProgressDialog(AddTaskActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
 /*   public void getStringRequest() {
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIURL.POST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("hzm", response);

                        hideDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("hzm", error.getMessage());
                        hideDialog();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("userId","1");
                map.put("summary","hussam");
                map.put("title","hussam");
                map.put("body","hussam");
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void showDialog() {
        progressDialog = new ProgressDialog(AddTaskActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }*/
  /*  Button button;
    TextView tvIsConnected;
    EditText etName;
    EditText etCountry;
    EditText etTwitter;

    TextView tvResult;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.add_task);
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
        etName = findViewById(R.id.etName);
        etCountry = findViewById(R.id.etCountry);
        etTwitter = findViewById(R.id.etTwitter);
        tvResult = (TextView) findViewById(R.id.tvResult);
        button = (Button) findViewById(R.id.btnSend);
 button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "{"+
                        "\"name\"" + "\"" + et_name.getText().toString() + "\","+
                        "\"job\"" + "\"" + et_job.getText().toString() + "\""+
                        "}";
                Submit(data);
            }
        });
    }
        checkNetworkConnection();
    }
    public boolean checkNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean isConnected = false;
        if (networkInfo != null && (isConnected = networkInfo.isConnected())) {
            // show "Connected" & type of network "WIFI or MOBILE"
            tvIsConnected.setText("Connected "+networkInfo.getTypeName());
            // change background color to red
            tvIsConnected.setBackgroundColor(0xFF7CCC26);


        } else {
            // show "Not Connected"
            tvIsConnected.setText("Not Connected");
            // change background color to green
            tvIsConnected.setBackgroundColor(0xFFFF0000);
        }

        return isConnected;
    }


    private void Submit(String data)
    {
        final String savedata= data;
        String URL="https://YOUR_API_URL";

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres=new JSONObject(response);
                    Toast.makeText(getApplicationContext(),objres.toString(),Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();

                }
                //Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                //Log.v("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return savedata == null ? null : savedata.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }

        };
        requestQueue.add(stringRequest);
    }*/


 /*   private String httpPost(String myUrl) throws IOException, JSONException {
        String result = "http://www.beststrends.com/tasks/api/index.php/addTask";

        URL url = new URL(myUrl);

        // 1. create HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        // 2. build JSON object
        JSONObject jsonObject = buidJsonObject();

        // 3. add JSON content to POST request body
        setPostRequestContent(conn, jsonObject);

        // 4. make POST request to the given URL
        conn.connect();

        // 5. return response message
        return conn.getResponseMessage()+"";

    }


    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                try {
                    return httpPost(urls[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            tvResult.setText(result);
        }
    }


    public void send(View view) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        // perform HTTP POST request
        if(checkNetworkConnection())
            new HTTPAsyncTask().execute("http://www.beststrends.com/tasks/api/index.php/addTask");
        else
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();

    }

    private JSONObject buidJsonObject() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("title", etName.getText().toString());
        jsonObject.accumulate("summary",  etCountry.getText().toString());
        jsonObject.accumulate("description",  etTwitter.getText().toString());

        return jsonObject;
    }

    private void setPostRequestContent(HttpURLConnection conn, JSONObject jsonObject) throws IOException {

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        Log.i(MainActivity.class.toString(), jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }

}*/
