package com.darko724.triviatest.Controller;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
    private static final String TAG = AppController.class.getSimpleName();// to get th name of class
private static AppController Instance;
private RequestQueue requestQueue;


public  static synchronized AppController getInstance(){
    return Instance; }

    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;
    }

    public  RequestQueue getRequestQueue(){
    if(requestQueue == null){
        requestQueue = Volley.newRequestQueue(this);
    }
    return  requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req,String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG: tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req){
    req.setTag(TAG);
    getRequestQueue().add(req);
    }
}