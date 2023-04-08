package com.inficta.alokitsupport.Repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inficta.alokitsupport.Model.CompanyModel;
import com.inficta.alokitsupport.Model.InTimeModel;
import com.inficta.alokitsupport.Model.LeaveListModel;
import com.inficta.alokitsupport.Model.OutTimeModel;
import com.inficta.alokitsupport.Model.YearModel;
import com.inficta.alokitsupport.Network.ApiClient;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceRepo {
    private static AttendanceRepo attendanceRepo;

    public static AttendanceRepo getInstance() {
        if (attendanceRepo == null) {
            attendanceRepo = new AttendanceRepo();
        }
        return attendanceRepo;
    }

    public LiveData<InTimeModel> inTime(Map<String, String> loginMap) {

        MutableLiveData<InTimeModel> responseModelMutableLiveData = new MutableLiveData<>();

        ApiClient.getAPIService().inTime(loginMap).enqueue(new Callback<InTimeModel>() {
            @Override
            public void onResponse(Call<InTimeModel> call, Response<InTimeModel> response) {
                if (response.isSuccessful()) {
                    responseModelMutableLiveData.postValue(response.body());
                } else {
                    response.body().setResult("Error");
                    responseModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<InTimeModel> call, Throwable t) {
            }
        });
        return responseModelMutableLiveData;
    }

    public LiveData<OutTimeModel> outTime(Map<String, String> loginMap) {

        MutableLiveData<OutTimeModel> responseModelMutableLiveData = new MutableLiveData<>();

        ApiClient.getAPIService().outTime(loginMap).enqueue(new Callback<OutTimeModel>() {
            @Override
            public void onResponse(Call<OutTimeModel> call, Response<OutTimeModel> response) {
                if (response.isSuccessful()) {
                    responseModelMutableLiveData.postValue(response.body());
                } else {
                    response.body().setResult("Error");
                    responseModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<OutTimeModel> call, Throwable t) {
            }
        });
        return responseModelMutableLiveData;
    }


    public LiveData<CompanyModel> getCompany() {
        MutableLiveData<CompanyModel> productModelMutableLiveData = new MutableLiveData<CompanyModel>();
        ApiClient.getAPIService().getCompany().enqueue(new Callback<CompanyModel>() {
            @Override
            public void onResponse(Call<CompanyModel> call, Response<CompanyModel> response) {
                if (response.isSuccessful()) {
                    productModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CompanyModel> call, Throwable t) {
            }
        });
        return productModelMutableLiveData;
    }

    public LiveData<LeaveListModel> getLeaveList() {
        MutableLiveData<LeaveListModel> productModelMutableLiveData = new MutableLiveData<LeaveListModel>();
        ApiClient.getAPIService().getLeaveList().enqueue(new Callback<LeaveListModel>() {
            @Override
            public void onResponse(Call<LeaveListModel> call, Response<LeaveListModel> response) {
                if (response.isSuccessful()) {
                    productModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<LeaveListModel> call, Throwable t) {
            }
        });
        return productModelMutableLiveData;
    }
}
