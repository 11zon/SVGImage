package com.elevenzon.svgimage;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<CountryModel> arrayList;
    ProgressDialog progressDialog;
    String URL = "https://restcountries.eu/rest/v2/all";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //set layout for RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        arrayList = new ArrayList<>();
        SendAndGetRequest();
    }

    public void SendAndGetRequest() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();
                Log.e("Error", response.toString());
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject=response.getJSONObject(i);
                        CountryModel model = new CountryModel();
                        model.setName(jsonObject.getString("name"));
                        model.setCallingCodes(jsonObject.getString("callingCodes").substring(2,(jsonObject.getString("callingCodes").length() - 2)));
                        model.setFlag(jsonObject.getString("flag"));
                        arrayList.add(model);
                    }
                } catch (JSONException  e) {
                    e.printStackTrace();
                }
                CountryAdapter adapter = new CountryAdapter(MainActivity.this, arrayList);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("Error", error.toString());
            }
        });
        // Access the RequestQueue through MyAppSingleton class.
        MySingleton.getInstance(this).addToRequestQueue(request);
    }
}