package com.inficta.alokitsupport.Repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inficta.alokitsupport.Model.AttendanceReportModel;
import com.inficta.alokitsupport.Model.CounterModel;
import com.inficta.alokitsupport.Model.ExpiredDateTimeModel;
import com.inficta.alokitsupport.Model.LeaveModel;
import com.inficta.alokitsupport.Model.PreviousLeaveModel;
import com.inficta.alokitsupport.Model.ProfileModel;
import com.inficta.alokitsupport.Model.SuportModel;
import com.inficta.alokitsupport.Network.ApiClient;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CounterRepo {
    private static CounterRepo attendanceRepo;

    public static CounterRepo getInstance() {
        if (attendanceRepo == null) {
            attendanceRepo = new CounterRepo();
        }
        return attendanceRepo;
    }

    public LiveData<CounterModel> counter(Map<String, String> loginMap) {

        MutableLiveData<CounterModel  > responseModelMutableLiveData = new MutableLiveData<>();

        ApiClient.getAPIService().counter(loginMap).enqueue(new Callback<CounterModel>() {
            @Override
            public void onResponse(Call<CounterModel> call, Response<CounterModel> response) {
                if (response.isSuccessful()) {
                    responseModelMutableLiveData.postValue(response.body());
                } else {
                    response.body().setResult("Error");
                    responseModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CounterModel> call, Throwable t) {
            }
        });
        return responseModelMutableLiveData;
    }

    public LiveData<SuportModel> support(Map<String, String> loginMap) {

        MutableLiveData<SuportModel> responseModelMutableLiveData = new MutableLiveData<>();

        ApiClient.getAPIService().support(loginMap).enqueue(new Callback<SuportModel>() {
            @Override
            public void onResponse(Call<SuportModel> call, Response<SuportModel> response) {
                if (response.isSuccessful()) {
                    responseModelMutableLiveData.postValue(response.body());
                } else {
                    response.body().setResult("Error");
                    responseModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SuportModel> call, Throwable t) {
            }
        });
        return responseModelMutableLiveData;
    }



    public LiveData<ProfileModel> profile(Map<String, String> loginMap) {

        MutableLiveData<ProfileModel> responseModelMutableLiveData = new MutableLiveData<>();

        ApiClient.getAPIService().profile(loginMap).enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if (response.isSuccessful()) {
                    responseModelMutableLiveData.postValue(response.body());
                } else {
                    response.body().setResult("Error");
                    responseModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
            }
        });
        return responseModelMutableLiveData;
    }

    public LiveData<ExpiredDateTimeModel> expiredDateTime(Map<String, String> loginMap) {

        MutableLiveData<ExpiredDateTimeModel> responseModelMutableLiveData = new MutableLiveData<>();

        ApiClient.getAPIService().expiredDateTime(loginMap).enqueue(new Callback<ExpiredDateTimeModel>() {
            @Override
            public void onResponse(Call<ExpiredDateTimeModel> call, Response<ExpiredDateTimeModel> response) {
                if (response.isSuccessful()) {
                    responseModelMutableLiveData.postValue(response.body());
                }/* else {
                    response.body().setResult("Error");
                    responseModelMutableLiveData.postValue(response.body());
                }*/
            }

            @Override
            public void onFailure(Call<ExpiredDateTimeModel> call, Throwable t) {
            }
        });
        return responseModelMutableLiveData;
    }

    public LiveData<LeaveModel> applyLeave(Map<String, String> loginMap) {

        MutableLiveData<LeaveModel> responseModelMutableLiveData = new MutableLiveData<>();

        ApiClient.getAPIService().applyLeave(loginMap).enqueue(new Callback<LeaveModel>() {
            @Override
            public void onResponse(Call<LeaveModel> call, Response<LeaveModel> response) {
                if (response.isSuccessful()) {
                    responseModelMutableLiveData.postValue(response.body());
                } else {
                    response.body().setResult("Error");
                    responseModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<LeaveModel> call, Throwable t) {
            }
        });
        return responseModelMutableLiveData;
    }

    public LiveData<AttendanceReportModel> aReport(Map<String, String> loginMap) {

        MutableLiveData<AttendanceReportModel> responseModelMutableLiveData = new MutableLiveData<>();

        ApiClient.getAPIService().aReport(loginMap).enqueue(new Callback<AttendanceReportModel>() {
            @Override
            public void onResponse(Call<AttendanceReportModel> call, Response<AttendanceReportModel> response) {
                if (response.isSuccessful()) {
                    responseModelMutableLiveData.postValue(response.body());
                } else {
                    response.body().setResult("Error");
                    responseModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AttendanceReportModel> call, Throwable t) {
            }
        });
        return responseModelMutableLiveData;
    }

    public LiveData<PreviousLeaveModel> leaveList(Map<String, String> loginMap) {

        MutableLiveData<PreviousLeaveModel> responseModelMutableLiveData = new MutableLiveData<>();

        ApiClient.getAPIService().leaveList(loginMap).enqueue(new Callback<PreviousLeaveModel>() {
            @Override
            public void onResponse(Call<PreviousLeaveModel> call, Response<PreviousLeaveModel> response) {
                if (response.isSuccessful()) {
                    responseModelMutableLiveData.postValue(response.body());
                } else {
                    response.body().setResult("Error");
                    responseModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PreviousLeaveModel> call, Throwable t) {
            }
        });
        return responseModelMutableLiveData;
    }

}
