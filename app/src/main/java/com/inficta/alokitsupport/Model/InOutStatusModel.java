package com.inficta.alokitsupport.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InOutStatusModel {
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

    public class Datum {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("in_out_status")
        @Expose
        private String inOutStatus;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getInOutStatus() {
            return inOutStatus;
        }

        public void setInOutStatus(String inOutStatus) {
            this.inOutStatus = inOutStatus;
        }

    }
}
