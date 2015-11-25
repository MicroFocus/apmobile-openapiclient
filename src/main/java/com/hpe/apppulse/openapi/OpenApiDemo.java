package com.hpe.apppulse.openapi;

import com.hpe.apppulse.openapi.apppulseopenapi.AppPulseOpenApiImp;
import com.hpe.apppulse.openapi.v1.bl.beans.DeviceOsPerformanceMatrixBean;
import com.hpe.apppulse.openapi.v1.bl.beans.FundexResponseBean;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Meir Ron on 10/20/2015.
 *
 * USAGE: You should input 4 parameters,
 *         Tenant ID, Client ID and Client Secret that can be taken from AppPulse settings.
 *         And From time: the first day period that the data will be shown.
 *         e.g. 17728942 17728942#C1 9292f441-04b5-46f3-87f6-e4eda6d5451f 2015-08-02
 * This example contains 4 steps:
 * 1) Authorise that get a token that will be use at all others steps.
 * 2) Get applications list.
 * 3) Get fundex scores for all applications
 * 4) Get Matrix of device-OS performance for the second application.
 *
 */
public class OpenApiDemo {

    final static String USAGE = "Parameters: <tenant id> <client id> <client secret> <from date>";

    public static void main(String[] args) {
        final String tenantId;
        final String clientId;
        final String clientSecret;
        final String fromDate;

        // Input validation
        if (args.length == 1) {
            String command = args[0];
            if (!command.equalsIgnoreCase("help")) {
                System.err.println("Unknown command: " + command);
            }
            System.out.println(USAGE);
            return;
        } else if (args.length == 4) {
            tenantId = args[0];
            clientId = args[1];
            clientSecret = args[2];
            fromDate = args[3];
        } else {
            System.err.println(USAGE);
            return;
        }

        // 1. Authorise step
        final String token;
        try {
            token = AppPulseOpenApiImp.getTokenFromAppPulseOpenAPI(tenantId, clientId, clientSecret);
        } catch (IOException e) {
            System.out.println("failed to authorise (get token)" + e.getMessage());;
            return;
        }
        System.out.println("token=" + token);
        printLine();
        // -----------------------------------------------------------------------------------

        // 2. get applications API
        final List<String> applications;
        try {
            applications = AppPulseOpenApiImp.getApplications(token, tenantId);
        } catch (IOException e) {
            System.out.println("failed to get applications" + e.getMessage());
            return;
        }
        System.out.println("applications=" + applications.stream().collect(Collectors.joining(",")));
        printLine();

        if(applications.isEmpty()) return;
        // -----------------------------------------------------------------------------------

        // 3. get the Fundex for each application
        for(String application:applications) {
            final FundexResponseBean.Data fundex;
            try {
                fundex = AppPulseOpenApiImp.getFundex(token, tenantId, application, fromDate);
            } catch (IOException e) {
                System.out.printf("failed to get fundex of application:%s%s%s%n", application, System.lineSeparator(), e.getMessage());
                return;
            }
            System.out.printf("Fundex for application %s:%s%s", application, System.lineSeparator(), fundex.toString());
        }
        printLine();
        // -----------------------------------------------------------------------------------

        // 4. get the device-OS performance matrix
        final String application = applications.get(1);
        final DeviceOsPerformanceMatrixBean.Data deviceOsPerformanceMatrix;
        try {
            deviceOsPerformanceMatrix = AppPulseOpenApiImp.getDeviceOsPerformanceMatrix(token, tenantId, application, fromDate);
        } catch (IOException e) {
            System.out.printf("failed to get DeviceOsPerformanceMatrix of application:%s%s%s%n", application, System.lineSeparator(), e.getMessage());
            return;
        }
        System.out.printf("DeviceOsPerformanceMatrix for application %s:%s%s", application, System.lineSeparator(), deviceOsPerformanceMatrix.toString());
    }

    private static void printLine() {
        System.out.println(System.lineSeparator() +
                "============================================================================================" +
                System.lineSeparator());
    }


}
