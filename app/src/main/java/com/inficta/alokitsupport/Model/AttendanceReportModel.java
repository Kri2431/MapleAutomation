package com.inficta.alokitsupport.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AttendanceReportModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private List<Datum> data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("in_time")
        @Expose
        private String inTime;
        @SerializedName("in_date")
        @Expose
        private String inDate;
        @SerializedName("in_plant_name")
        @Expose
        private String inPlantName;
        @SerializedName("out_time")
        @Expose
        private String outTime;
        @SerializedName("out_date")
        @Expose
        private String outDate;
        @SerializedName("out_plant_name")
        @Expose
        private String outPlantName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getInTime() {
            return inTime;
        }

        public void setInTime(String inTime) {
            this.inTime = inTime;
        }

        public String getInDate() {
            return inDate;
        }

        public void setInDate(String inDate) {
            this.inDate = inDate;
        }

        public String getInPlantName() {
            return inPlantName;
        }

        public void setInPlantName(String inPlantName) {
            this.inPlantName = inPlantName;
        }

        public String getOutTime() {
            return outTime;
        }

        public void setOutTime(String outTime) {
            this.outTime = outTime;
        }

        public String getOutDate() {
            return outDate;
        }

        public void setOutDate(String outDate) {
            this.outDate = outDate;
        }

        public String getOutPlantName() {
            return outPlantName;
        }

        public void setOutPlantName(String outPlantName) {
            this.outPlantName = outPlantName;
        }

    }
}
