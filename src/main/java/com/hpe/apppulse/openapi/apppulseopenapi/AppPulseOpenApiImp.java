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
     * Before sending a REST request, you must first get a token.
     * This method responsible for achieve it by sending URL:
     * <SaaS domain>/mobile/openapi/rest/v1/<tenant ID>/oauth/token as POST
     * with body: {"clientSecret": "<client secret>", "clientId": "<client ID>"}
     * This generates a token with an expiration time-stamp. For example:
     *   {"token":"123455_f48720f4-c64c-4aec-a697-7f73807601f8","expirationTime":1441202451284}
     * The answer is parsing and returns the token as string
     * @param tenantId
     * @param clientId
     * @param clientSecret
     * @return token as String.
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
     * Get a list of applications for the tenant.
     * For each application it returns the follow fields:
     *   Application name
     *   Application ID
     *   Application package name
     *   OS platform (iOS/Android)
     *   other metrics for Application
     * @param token
     * @param tenantId
     * @return List of ApplicationsDataBean
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
     * Get an application’s Fundex score and breakdown scores.
     * Using URL: <SaaS domain>/mobile/openapi/rest/v1/<tenant ID>/applications/
     *            <app ID>/metrics/fundex/overview?from_day=<yyyy-mm-dd>&to_day=<yyyy-mmdd>
     * Returns the following fields:
     *   Fundex score
     *   Slow UI points
     *   Slow launch points
     *   Crashes points
     *   Errors points
     *   Heavy battery usage points
     *   Heavy cellular usage points
     * @param token
     * @param tenantId
     * @param actionId
     * @param date Get details on performance of different devices and OS versions.
     * @return Fundex as FundexResponseBean
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
     * Get details on performance of different devices and OS versions.
     * the results will be arrange as matrix of devices and for each it will
     * detail each of OS with its metrics that was in use.
     * each device (parent) and its sons (OS) will contains the follow fields:
     *   osName
     *   osVersion
     *   dailyAvgUsers
     *   avgResponseTimeSec
     *
     * @param token
     * @param tenantId
     * @param actionId
     * @param date Get details on performance of different devices and OS versions.
     * @return DeviceOsPerformanceMatrixBean ( matrix of device with details of all
     *         its OSs including measurements)
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
