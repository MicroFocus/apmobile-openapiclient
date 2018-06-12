package com.hpe.apppulse.openapi;

import com.hpe.apppulse.openapi.apppulseopenapi.AppPulseOpenApiImp;
import com.hpe.apppulse.openapi.v1.bl.beans.DeviceOsPerformanceMatrixBean;
import com.hpe.apppulse.openapi.v1.bl.beans.FundexResponseBean;
import org.apache.commons.cli.*;

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

    final static String USAGE = "Parameters: <tenant id> <client id> <client secret> <from date> <proxy>";

    public static void main(String[] args) {
        final String tenantId;
        final String clientId;
        final String clientSecret;
        final String fromDate;

        Options options = generateCommandLineOptions();

        CommandLineParser parser = new BasicParser();

        try {
            CommandLine line = parser.parse(options, args);

            if(line.hasOption("help")){
                printUsage(options);
                return;
            }

            clientId = line.getOptionValue("clientId");
            tenantId = line.getOptionValue("tenantId");
            clientSecret = line.getOptionValue("secret");
            fromDate = line.getOptionValue("fromDate");

            if(line.hasOption("proxy")){
                String proxyStr = line.getOptionValue("proxy");
                AppPulseOpenApiImp.setProxy(proxyStr);
            }



        }catch (Exception ex){
            System.err.println("Error parsing arguments!\n" + ex.getMessage());
            printUsage(options);
            return;
        }

        // 1. Authenticate step
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
            System.out.printf("Fundex for application %s:%s%s%s", application, System.lineSeparator(), fundex==null?"Not generated":fundex.toString(), System.lineSeparator());
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

    private static void printUsage(Options options){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "AppPulseOpenApi examples usage:", options );
    }

    private static Options generateCommandLineOptions(){

        Options options =new Options();

        options.addOption(OptionBuilder.withLongOpt("help")
                .withDescription("Print this message")
                .hasArg()
                .isRequired(false)
                .create('h'));

        options.addOption(OptionBuilder.withLongOpt("tenantId")
                .withValueSeparator()
                .withDescription("Tenant Id")
                .hasArg()
                .isRequired(true)
                .create());

        options.addOption(OptionBuilder.withLongOpt("clientId")
                .withValueSeparator()
                .withDescription("Client Id")
                .hasArg()
                .isRequired(true)
                .create());

        options.addOption(OptionBuilder.withLongOpt("secret")
                .withValueSeparator()
                .withDescription("Client secrent")
                .hasArg()
                .isRequired(true)
                .create());

        options.addOption(OptionBuilder.withLongOpt("fromDate")
                .withValueSeparator()
                .withDescription("Date to calculate the data from (yyyy-mm-dd)")
                .hasArg()
                .isRequired(true)
                .create());

        options.addOption(OptionBuilder.withLongOpt("proxy")
                .withValueSeparator()
                .withDescription("proxy hostname and port (proxyhost:port)")
                .hasArg()
                .isRequired(false)
                .create());

        return options;
    }
}
