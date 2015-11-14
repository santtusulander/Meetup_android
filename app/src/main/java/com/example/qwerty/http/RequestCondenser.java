package com.example.qwerty.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qwerty on 14.11.2015.
 */
public class RequestCondenser {
    Request.Method reqMethod;
    JSONObject body;
    String url;
    String TAG;
    Context ctx;

    public RequestCondenser(Request.Method method, JSONObject obj, String url, String TAG, Context ctx) {
        this.reqMethod = method;
        this.body = obj;
        this.url = url;
        this.TAG = TAG;
        this.ctx = ctx;
    }

    public void reqBodySetter(JSONObject body) {
        this.body = body;
    }

    public void reqUrlSetter(String url) {
        this.url = url;
    }

    interface MyCallback {
        void callbackCall();
    }

    public void request(final MyCallback cb) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        cb.callbackCall();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG, "" + error.getMessage() + "," + error.toString());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("charset", "utf-8");
                return headers;
            }
        };
        MySingleton.getInstance(ctx).addToRequestQueue(req);
    }
}
