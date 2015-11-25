package com.hpe.apppulse.openapi.v1.bl.beans;

import java.util.List;

/**
 * Created by Meir Ron on 10/21/2015.
 */
public class AppsResponseBean {
    private MetaDataBean metaData;
    private ApplicationsDataBean data;

    public AppsResponseBean() {
    }

    public MetaDataBean getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaDataBean metaData) {
        this.metaData = metaData;
    }

    public ApplicationsDataBean getData() {
        return data;
    }



    public void setData(ApplicationsDataBean data) {
        this.data = data;
    }

    public class MetaDataBean {
        private String message;
        private int messageCode;

        public MetaDataBean() {
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getMessageCode() {
            return messageCode;
        }

        public void setMessageCode(int messageCode) {
            this.messageCode = messageCode;
        }
    }

    public class ApplicationsDataBean {
        private List<ApplicationBean> applications;

        public ApplicationsDataBean() {
        }

        public List<ApplicationBean> getApplications() {
            return applications;
        }

        public void setApplications(List<ApplicationBean> applications) {
            this.applications = applications;
        }

        public class ApplicationBean {
            private String applicationId;
            private String applicationName;
            private String platformType;
            private String packageName;
            private float launchTimeThresholdSec;
            private float actionResponseTimeThresholdSec;
            private double batteryUsageThresholdPercentPerMinute;
            private long cellularDataUsageThresholdKilobytePerMinute;

            public ApplicationBean() {
            }

            public String getApplicationId() {
                return applicationId;
            }

            public void setApplicationId(String applicationId) {
                this.applicationId = applicationId;
            }

            public String getApplicationName() {
                return applicationName;
            }

            public void setApplicationName(String applicationName) {
                this.applicationName = applicationName;
            }

            public String getPlatformType() {
                return platformType;
            }

            public void setPlatformType(String platformType) {
                this.platformType = platformType;
            }

            public String getPackageName() {
                return packageName;
            }

            public void setPackageName(String packageName) {
                this.packageName = packageName;
            }

            public float getActionResponseTimeThresholdSec() {
                return actionResponseTimeThresholdSec;
            }

            public void setActionResponseTimeThresholdSec(float actionResponseTimeThresholdSec) {
                this.actionResponseTimeThresholdSec = actionResponseTimeThresholdSec;
            }

            public float getLaunchTimeThresholdSec() {
                return launchTimeThresholdSec;
            }

            public void setLaunchTimeThresholdSec(float launchTimeThresholdSec) {
                this.launchTimeThresholdSec = launchTimeThresholdSec;
            }

            public double getBatteryUsageThresholdPercentPerMinute() {
                return batteryUsageThresholdPercentPerMinute;
            }

            public void setBatteryUsageThresholdPercentPerMinute(double batteryUsageThresholdPercentPerMinute) {
                this.batteryUsageThresholdPercentPerMinute = batteryUsageThresholdPercentPerMinute;
            }

            public long getCellularDataUsageThresholdKilobytePerMinute() {
                return cellularDataUsageThresholdKilobytePerMinute;
            }

            public void setCellularDataUsageThresholdKilobytePerMinute(long cellularDataUsageThresholdKilobytePerMinute) {
                this.cellularDataUsageThresholdKilobytePerMinute = cellularDataUsageThresholdKilobytePerMinute;
            }
        }
    }
}