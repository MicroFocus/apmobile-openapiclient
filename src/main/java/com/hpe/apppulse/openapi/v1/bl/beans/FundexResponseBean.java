package com.hpe.apppulse.openapi.v1.bl.beans;

/**
 * Created by Meir Ron on 10/21/2015.
 */

public final class FundexResponseBean {
    public MetaData metaData;
    public Data data;

    public FundexResponseBean() {
    }

    public FundexResponseBean(MetaData metaData, Data data){
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

        public String getFromDay() {
            return fromDay;
        }

        public void setFromDay(String fromDay) {
            this.fromDay = fromDay;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public long getMessageCode() {
            return messageCode;
        }

        public void setMessageCode(long messageCode) {
            this.messageCode = messageCode;
        }

        public String getToDay() {
            return toDay;
        }

        public void setToDay(String toDay) {
            this.toDay = toDay;
        }
    }

    /**
     * This is the bean that returns from: Get an application's Fundex score and breakdown scores.
     * Returns the following fields:
     *  Fundex score
     *  Slow UI points
     *  Slow launch points
     *  Crashes points
     *  Errors points
     *  Heavy battery usage points
     *  Heavy cellular usage points
     */
    public static final class Data {
        public long fundexScorePoints;
        public long slowUiPoints;
        public long slowLaunchPoints;
        public long crashesPoints;
        public long errorsPoints;
        public long heavyBatteryUsagePoints;
        public long heavyCellularUsagePoints;

        public Data(){
        }

        public long getCrashesPoints() {
            return crashesPoints;
        }

        public void setCrashesPoints(long crashesPoints) {
            this.crashesPoints = crashesPoints;
        }

        public long getErrorsPoints() {
            return errorsPoints;
        }

        public void setErrorsPoints(long errorsPoints) {
            this.errorsPoints = errorsPoints;
        }

        public long getFundexScorePoints() {
            return fundexScorePoints;
        }

        public void setFundexScorePoints(long fundexScorePoints) {
            this.fundexScorePoints = fundexScorePoints;
        }

        public long getHeavyBatteryUsagePoints() {
            return heavyBatteryUsagePoints;
        }

        public void setHeavyBatteryUsagePoints(long heavyBatteryUsagePoints) {
            this.heavyBatteryUsagePoints = heavyBatteryUsagePoints;
        }

        public long getHeavyCellularUsagePoints() {
            return heavyCellularUsagePoints;
        }

        public void setHeavyCellularUsagePoints(long heavyCellularUsagePoints) {
            this.heavyCellularUsagePoints = heavyCellularUsagePoints;
        }

        public long getSlowLaunchPoints() {
            return slowLaunchPoints;
        }

        public void setSlowLaunchPoints(long slowLaunchPoints) {
            this.slowLaunchPoints = slowLaunchPoints;
        }

        public long getSlowUiPoints() {
            return slowUiPoints;
        }

        public void setSlowUiPoints(long slowUiPoints) {
            this.slowUiPoints = slowUiPoints;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "crashesPoints=" + crashesPoints +
                    ", fundexScorePoints=" + fundexScorePoints +
                    ", slowUiPoints=" + slowUiPoints +
                    ", slowLaunchPoints=" + slowLaunchPoints +
                    ", errorsPoints=" + errorsPoints +
                    ", heavyBatteryUsagePoints=" + heavyBatteryUsagePoints +
                    ", heavyCellularUsagePoints=" + heavyCellularUsagePoints +
                    '}';
        }
    }
}