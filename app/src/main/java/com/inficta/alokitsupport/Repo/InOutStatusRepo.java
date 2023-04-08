package com.inficta.alokitsupport.Repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inficta.alokitsupport.Model.InOutStatusModel;
import com.inficta.alokitsupport.Network.ApiClient;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InOutStatusRepo {
    private static InOutStatusRepo attendanceRepo;

    public static InOutStatusRepo getInstance() {
        if (attendanceRepo == null) {
            attendanceRepo = new InOutStatusRepo();
        }
        return attendanceRepo;
    }

    public LiveData<InOutStatusModel> inOutStatus(Map<String, String> loginMap) {

        MutableLiveData<InOutStatusModel> responseModelMutableLiveData = new MutableLiveData<>();

        ApiClient.getAPIService().inOutStatus(loginMap).enqueue(new Callback<InOutStatusModel>() {
            @Override
            public void onResponse(Call<InOutStatusModel> call, Response<InOutStatusModel> response) {
                if (response.isSuccessful()) {
                    responseModelMutableLiveData.postValue(response.body());
                } else {
                    response.body().setResult("Error");
                    responseModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<InOutStatusModel> call, Throwable t) {
            }
        });
        return responseModelMutableLiveData;
    }

}
