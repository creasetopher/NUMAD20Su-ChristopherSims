package com.example.numad20su_christophersims.services;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Objects;

public class CurrencyService {

    public static final String ACCESS_KEY = "f2eb3303e03e1076f308c8554eb91a97";
    public static final String BASE_URL = "http://api.currencylayer.com/";
    public static final String ENDPOINT = "live";
    private String reqUrl;
    public String response;

    public CurrencyService() {
        this.reqUrl = String.format("%s%s?access_key=%s", BASE_URL, ENDPOINT, ACCESS_KEY);
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void makeConversionRequest(float amount, String dest,  RequestQueue queue) {

        String toCurrency = String.format("&currencies=%s", dest);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.reqUrl + toCurrency,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject responseObj = new JSONObject(response);
                            JSONObject field = (JSONObject)responseObj.get("quotes");
                            Iterator result = field.keys();
                            while (result.hasNext()) {
                                String r = result.next().toString();
                                String convertedAMount = field.get(r).toString();

                                CurrencyService.this.setResponse(
                                        convertedAMount
                                );
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CurrencyService.this.setResponse(response);

            }
        });
        queue.add(stringRequest);
    }

    public String getConversion() {
        return this.response;
    }


}
