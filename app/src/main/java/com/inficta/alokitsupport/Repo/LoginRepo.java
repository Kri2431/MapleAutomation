package com.inficta.alokitsupport.Repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inficta.alokitsupport.Model.LoginModel;
import com.inficta.alokitsupport.Model.YearModel;
import com.inficta.alokitsupport.Network.ApiClient;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepo {
    private static LoginRepo loginRepository;

    public static LoginRepo getInstance() {
        if (loginRepository == null) {
            loginRepository = new LoginRepo();
        }
        return loginRepository;
    }

    public LiveData<LoginModel> login(Map<String, String> loginMap) {

        MutableLiveData<LoginModel> responseModelMutableLiveData = new MutableLiveData<>();

        ApiClient.getAPIService().login(loginMap).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    responseModelMutableLiveData.postValue(response.body());
                } else {
                    response.body().setResult("Error");
                    responseModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
            }
        });
        return responseModelMutableLiveData;
    }


    public LiveData<YearModel> getYear() {
        MutableLiveData<YearModel> productModelMutableLiveData = new MutableLiveData<YearModel>();
        ApiClient.getAPIService().getYear().enqueue(new Callback<YearModel>() {
            @Override
            public void onResponse(Call<YearModel> call, Response<YearModel> response) {
                if (response.isSuccessful()) {
                    productModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<YearModel> call, Throwable t) {
            }
        });
        return productModelMutableLiveData;
    }
}
