package com.hpe.apppulse.openapi.Utils;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Created by Meir Ron on 11/12/2015.
 */
public class HttpUtils {

    public static void prepareRequest(String token, HttpGet getSamples){
        setHeaderToken(getSamples, token);
        getSamples.addHeader("Accept", "application/json");
    }

    public static HttpRequestBase prepareHttpRequestBase(HttpRequestBase requestBase,RequestConfig config){
        requestBase.setConfig(config);
        return  requestBase;
    }

    private static void setHeaderToken(HttpGet httpGet, String token){
        httpGet.setHeader("Authorization", "Bearer " + token);
    }


}
