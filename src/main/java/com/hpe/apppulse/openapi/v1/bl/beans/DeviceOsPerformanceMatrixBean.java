package com.hpe.apppulse.openapi.v1.bl.beans;

import java.util.Arrays;

/**
 * Created by Meir Ron on 11/12/2015.
 */
public class DeviceOsPerformanceMatrixBean {
    public MetaData metaData;
    public Data data;

    public DeviceOsPerformanceMatrixBean() {
    }

    public DeviceOsPerformanceMatrixBean(MetaData metaData, Data data){
        this.metaData = metaData;
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }


    public static final class MetaData {
        public String message;
        public long messageCode;
        public String fromDay;
        public String toDay;

        public MetaData(){
        }

        public MetaData(String message, long messageCode, String fromDay, String toDay){
            this.message = message;
            this.messageCode = messageCode;
            this.fromDay = fromDay;
            this.toDay = toDay;
        }
    }

    public static final class Data {
        public DeviceList deviceList[];

        public Data(){
        }

        public Data(DeviceList[] deviceList){
            this.deviceList = deviceList;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Devices List:").append(System.lineSeparator())
                    .append("--------------------")
                    .append(System.lineSeparator());
            for (DeviceList device : deviceList) {
                sb.append(device);
            }
            return sb.toString();
        }

        public static final class DeviceList {
            public final String deviceModel;
            public final String deviceVendor;
            public final float dailyAvgUsers;
            public final float avgResponseTimeSec;
            public final OsList osList[];

            public DeviceList(String deviceModel, String deviceVendor, float dailyAvgUsers, float avgResponseTimeSec, OsList[] osList){
                this.deviceModel = deviceModel;
                this.deviceVendor = deviceVendor;
                this.dailyAvgUsers = dailyAvgUsers;
                this.avgResponseTimeSec = avgResponseTimeSec;
                this.osList = osList;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("Device:{" + " deviceModel='").append(deviceModel).append('\'')
                        .append(", deviceVendor='").append(deviceVendor).append('\'')
                        .append(", dailyAvgUsers=").append(dailyAvgUsers)
                        .append(", avgResponseTimeSec=").append(avgResponseTimeSec).append('}')
                        .append(System.lineSeparator());
                for (OsList os : osList) {
                    sb.append(os);
                }
                sb.append("...........").append(System.lineSeparator());
                return sb.toString();
            }

            public static final class OsList {
                public final String osName;
                public final String osVersion;
                public final float dailyAvgUsers;
                public final float avgResponseTimeSec;

                public OsList(String osName, String osVersion, float dailyAvgUsers, float avgResponseTimeSec){
                    this.osName = osName;
                    this.osVersion = osVersion;
                    this.dailyAvgUsers = dailyAvgUsers;
                    this.avgResponseTimeSec = avgResponseTimeSec;
                }

                @Override
                public String toString() {
                    return ("Os:{" + " osName='") + osName + '\'' + ", osVersion='" +
                            osVersion + '\'' + ", dailyAvgUsers=" + dailyAvgUsers +
                            ", avgResponseTimeSec=" + avgResponseTimeSec + '}' +
                            System.lineSeparator();
                }
            }
        }
    }
}
