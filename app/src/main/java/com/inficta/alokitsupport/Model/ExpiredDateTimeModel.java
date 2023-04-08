package com.inficta.alokitsupport.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExpiredDateTimeModel {
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

    public class Datum implements Serializable {

        @SerializedName("sr_no")
        @Expose
        private String srNo;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("issue_id")
        @Expose
        private String issueId;
        @SerializedName("assets_no.")
        @Expose
        private String assetsNo;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("end_time")
        @Expose
        private String endTime;
        @SerializedName("currtent_date_time")
        @Expose
        private String currtentDateTime;

        public String getSrNo() {
            return srNo;
        }

        public void setSrNo(String srNo) {
            this.srNo = srNo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getIssueId() {
            return issueId;
        }

        public void setIssueId(String issueId) {
            this.issueId = issueId;
        }

        public String getAssetsNo() {
            return assetsNo;
        }

        public void setAssetsNo(String assetsNo) {
            this.assetsNo = assetsNo;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getCurrtentDateTime() {
            return currtentDateTime;
        }

        public void setCurrtentDateTime(String currtentDateTime) {
            this.currtentDateTime = currtentDateTime;
        }

    }
}
