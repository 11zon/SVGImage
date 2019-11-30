package com.elevenzon.svgimage;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {

    private static MySingleton instance;
    private RequestQueue requestQueue;
    private static Context conTxt;
    
    public MySingleton(Context context) {
        conTxt = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new MySingleton(context);
        }
        return instance;
    }
    
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(conTxt);
        }
        return requestQueue;
    }
    
    public <T> void addToRequestQueue(Request request) {
        getRequestQueue().add(request);
    }
}