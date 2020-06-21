package com.example.numad20su_christophersims.services;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class CurrencyService {

    public static final String ACCESS_KEY = "f2eb3303e03e1076f308c8554eb91a97";
    public static final String BASE_URL = "http://api.currencylayer.com/";
    public static final String ENDPOINT = "convert";
    private String reqUrl;
    public String response = "";

    public CurrencyService() {
        this.reqUrl = String.format("%s%s?access_key=%s");
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void makeConversionRequest(float amount, String dest,  RequestQueue queue) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.reqUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        CurrencyService.this.setResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CurrencyService.this.setResponse(response);
            }
        });
    }

    public String getConversion() {
        return this.response;
    }


}
