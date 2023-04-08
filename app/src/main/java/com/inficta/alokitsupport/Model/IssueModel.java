package com.inficta.alokitsupport.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class IssueModel {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

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

    public class Datum implements Serializable{

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("engineer_id")
        @Expose
        private String engineerId;
        @SerializedName("engineer_name")
        @Expose
        private String engineerName;
        @SerializedName("assets_name")
        @Expose
        private String assetsName;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("as_un_id")
        @Expose
        private String asUnId;
        @SerializedName("warranty")
        @Expose
        private String warranty;
        @SerializedName("issue")
        @Expose
        private String issue;
        @SerializedName("issue_description")
        @Expose
        private String issueDescription;
        @SerializedName("service_status")
        @Expose
        private String serviceStatus;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("remark")
        @Expose
        private String remark;
        @SerializedName("er_udpdate_status")
        @Expose
        private String erUdpdateStatus;
        @SerializedName("assets_issue_id")
        @Expose
        private String assetsIssueId;
        @SerializedName("date")
        @Expose
        private String date;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getEngineerId() {
            return engineerId;
        }

        public void setEngineerId(String engineerId) {
            this.engineerId = engineerId;
        }

        public String getEngineerName() {
            return engineerName;
        }

        public void setEngineerName(String engineerName) {
            this.engineerName = engineerName;
        }

        public String getAssetsName() {
            return assetsName;
        }

        public void setAssetsName(String assetsName) {
            this.assetsName = assetsName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAsUnId() {
            return asUnId;
        }

        public void setAsUnId(String asUnId) {
            this.asUnId = asUnId;
        }

        public String getWarranty() {
            return warranty;
        }

        public void setWarranty(String warranty) {
            this.warranty = warranty;
        }

        public String getIssue() {
            return issue;
        }

        public void setIssue(String issue) {
            this.issue = issue;
        }

        public String getIssueDescription() {
            return issueDescription;
        }

        public void setIssueDescription(String issueDescription) {
            this.issueDescription = issueDescription;
        }

        public String getServiceStatus() {
            return serviceStatus;
        }

        public void setServiceStatus(String serviceStatus) {
            this.serviceStatus = serviceStatus;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getErUdpdateStatus() {
            return erUdpdateStatus;
        }

        public void setErUdpdateStatus(String erUdpdateStatus) {
            this.erUdpdateStatus = erUdpdateStatus;
        }

        public String getAssetsIssueId() {
            return assetsIssueId;
        }

        public void setAssetsIssueId(String assetsIssueId) {
            this.assetsIssueId = assetsIssueId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }
}
