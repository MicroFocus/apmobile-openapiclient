package com.hpe.apppulse.openapi.apppulseopenapi;

import com.hpe.apppulse.openapi.Utils.HttpUtils;
import com.hpe.apppulse.openapi.Utils.JsonUtils;
import com.hpe.apppulse.openapi.v1.bl.beans.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Meir Ron on 11/12/2015.
 */
public class AppPulseOpenApiImp {

    private static HttpClient httpClient = HttpClientBuilder.create().setDefaultCookieStore(new BasicCookieStore()).build();;

    /**
     *
     * @param tenantId
     * @param clientId
     * @param clientSecret
     * @return
     * @throws IOException
     */
    public static String getTokenFromAppPulseOpenAPI(String tenantId, String clientId, String clientSecret) throws IOException {
        System.out.println("Starting getTokenFromAppPulseOpenAPI ... ");

        final String getTokenUrl = String.format("%s/mobile/openapi/rest/%s/%s/oauth/token", constants.APPPULSE_URL_PREFIX, constants.OPEN_API_VERSION, tenantId);
        System.out.println("Using url: " + getTokenUrl);

        // prepare post
        HttpPost httppost = new HttpPost(getTokenUrl);

        final String postParameters = String.format("{\"clientId\": \"%s\", \"clientSecret\": \"%s\"}", clientId, clientSecret);

        // support json content type
        httppost.addHeader("Content-Type", "application/json;charset=UTF-8");

        try{
            httppost.setEntity(new ByteArrayEntity(postParameters.getBytes("UTF-8")));
        }
        catch(UnsupportedEncodingException e){
            System.out.printf("Failed to set entity: %s%s%s%n", postParameters, System.lineSeparator(), e.getMessage());
            throw e;
        }

        // Execute HTTP Post Request
        HttpResponse response = null;
        try{
            try{
                response = httpClient.execute(httppost);
            }
            catch(IOException e){
                System.out.printf(String.format("Failed to execute getTokenFromAppPulseOpenAPI:getToken%s%s", System.lineSeparator(), e.getMessage()));
                throw e;
            }

            // Analyze response
            if(response.getStatusLine().getStatusCode() != 200){
                JsonUtils.ResponseJsonReader<AppsResponseBean.MetaDataBean> jsonReader = new JsonUtils.ResponseJsonReader<>();

                System.err.println("Failed to execute getTokenFromAppPulseOpenAPI:getToken got status code: " + response.getStatusLine().getStatusCode() +
                        System.lineSeparator() + "Reason: " + response.getStatusLine().getReasonPhrase() +
                        System.lineSeparator() + jsonReader.readFromJson(response, AppsResponseBean.MetaDataBean.class));
                return constants.NO_ANSWER;
            }

            // Build answer
            JsonUtils.ResponseJsonReader<TokenBean> jsonReader = new JsonUtils.ResponseJsonReader<>();
            return jsonReader.readFromJson(response, TokenBean.class).getToken();

        } finally {
            if (response != null) {
                response.getEntity().getContent().close();
            }
            System.out.println("... getTokenFromAppPulseOpenAPI - Completed");
        }
    }

    /**
     *
     * @param token
     * @param tenantId
     * @return
     * @throws IOException
     */
    public static List<String> getApplications(String token, String tenantId) throws IOException{

        final String getApplicationsUrl = String.format("%s/mobile/openapi/rest/%s/%s/applications", constants.APPPULSE_URL_PREFIX, constants.OPEN_API_VERSION, tenantId);
        System.out.println("Starting getApplications using url: " + getApplicationsUrl);

        final HttpGet getApiRestCall = new HttpGet(getApplicationsUrl);
        HttpUtils.prepareRequest(token, getApiRestCall);

        // Execute HTTP Post Request
        HttpResponse response = null;
        try{
            try{
                response = httpClient.execute(getApiRestCall);
            }
            catch(IOException e){
                System.err.printf("Failed to execute getSample: %s%s%s%n", getApiRestCall.toString(), System.lineSeparator(), e.getMessage());
                throw e;
            }

            // Analyze response
            if(response.getStatusLine().getStatusCode() != 200){
                System.err.printf("Failed to execute getSample, got status code: %d%s%s%n", response.getStatusLine().getStatusCode(), System.lineSeparator(), getApiRestCall.toString());
                return Collections.emptyList();
            }

            JsonUtils.ResponseJsonReader<AppsResponseBean> jsonReader = new JsonUtils.ResponseJsonReader<>();
            final List<AppsResponseBean.ApplicationsDataBean.ApplicationBean> applications = jsonReader.readFromJson(response, AppsResponseBean.class).getData().getApplications();
            return applications.stream().map(AppsResponseBean.ApplicationsDataBean.ApplicationBean::getApplicationId).collect(Collectors.toList());
        }
        finally{
            if(response != null){
                response.getEntity().getContent().close();
            }
            System.out.println("... getApplications - Completed");
        }
    }


    /**
     *
     * @param token
     * @param tenantId
     * @param actionId
     * @param date
     * @return
     * @throws IOException
     */
    public static FundexResponseBean.Data getFundex(String token, String tenantId, String actionId, String date) throws IOException{

        final String getFundexUrl = String.format("%s/mobile/openapi/rest/%s/%s/applications/%s/metrics/fundex/overview?from_day=%s",
                constants.APPPULSE_URL_PREFIX, constants.OPEN_API_VERSION, tenantId, actionId, date);

        System.out.println("Starting getFundex using url: " + getFundexUrl);

        final HttpGet getApiRestCall = new HttpGet(getFundexUrl);
        HttpUtils.prepareRequest(token, getApiRestCall);

        // Execute HTTP Post Request
        HttpResponse response = null;
        try{
            try{
                response = httpClient.execute(getApiRestCall);
            }
            catch(IOException e){
                System.err.printf("Failed to execute getFundex: %s%s%s%n", getApiRestCall.toString(), System.lineSeparator(), e.getMessage());
                throw e;
            }

            // Analyze response
            if(response.getStatusLine().getStatusCode() != 200){
                System.err.printf("Failed to execute getFundex, got status code: %d%s%s%n", response.getStatusLine().getStatusCode(), System.lineSeparator(), getApiRestCall.toString());
                return new FundexResponseBean.Data();
            }

            JsonUtils.ResponseJsonReader<FundexResponseBean> jsonReader = new JsonUtils.ResponseJsonReader<>();
            return jsonReader.readFromJson(response, FundexResponseBean.class).getData();
        }
        finally{
            if(response != null){
                response.getEntity().getContent().close();
            }
            System.out.println("... getFundex - Completed");
        }
    }

    /**
     *
     * @param token
     * @param tenantId
     * @param actionId
     * @param date
     * @return
     * @throws IOException
     */
    public static DeviceOsPerformanceMatrixBean.Data getDeviceOsPerformanceMatrix(String token, String tenantId, String actionId, String date) throws IOException{

        final String getDeviceOsPerformanceMatrixUrl = String.format("%s/mobile/openapi/rest/%s/%s/applications/%s/metrics/deviceos/performance?from_day=%s",
                constants.APPPULSE_URL_PREFIX, constants.OPEN_API_VERSION, tenantId, actionId, date);

        System.out.println("Starting getDeviceOsPerformanceMatrix using url: " + getDeviceOsPerformanceMatrixUrl);

        final HttpGet getApiRestCall = new HttpGet(getDeviceOsPerformanceMatrixUrl);
        HttpUtils.prepareRequest(token, getApiRestCall);

        // Execute HTTP Post Request
        HttpResponse response = null;
        try{
            try{
                response = httpClient.execute(getApiRestCall);
            }
            catch(IOException e){
                System.err.printf("Failed to execute getDeviceOsPerformanceMatrix: %s%s%s%n", getApiRestCall.toString(), System.lineSeparator(), e.getMessage());
                throw e;
            }

            // Analyze response
            if(response.getStatusLine().getStatusCode() != 200){
                System.err.printf("Failed to execute getDeviceOsPerformanceMatrix, got status code: %d%s%s%n", response.getStatusLine().getStatusCode(), System.lineSeparator(), getApiRestCall.toString());
                return new DeviceOsPerformanceMatrixBean.Data();
            }

            JsonUtils.ResponseJsonReader<DeviceOsPerformanceMatrixBean> jsonReader = new JsonUtils.ResponseJsonReader<>();
            return jsonReader.readFromJson(response, DeviceOsPerformanceMatrixBean.class).getData();
        }
        finally{
            if(response != null){
                response.getEntity().getContent().close();
            }
            System.out.println("... getDeviceOsPerformanceMatrix - Completed");
        }
    }

}
