package com.hpe.apppulse.openapi.Utils;

import org.apache.http.client.methods.HttpGet;

/**
 * Created by Meir Ron on 11/12/2015.
 */
public class HttpUtils {
    public static void prepareRequest(String token, HttpGet getSamples){
        setHeaderToken(getSamples, token);
        getSamples.addHeader("Accept", "application/json");
    }

    private static void setHeaderToken(HttpGet httpGet, String token){
        httpGet.setHeader("Authorization", "Bearer " + token);
    }
}
