package com.inficta.alokitsupport.Repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inficta.alokitsupport.Model.IssueModel;
import com.inficta.alokitsupport.Model.SearchModel;
import com.inficta.alokitsupport.Model.StstusModel;
import com.inficta.alokitsupport.Model.UpdateModel;
import com.inficta.alokitsupport.Network.ApiClient;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IssueRepo {
    private static IssueRepo issueRepo;

    public static IssueRepo getInstance() {
        if (issueRepo == null) {
            issueRepo = new IssueRepo();
        }
        return issueRepo;
    }


    public LiveData<SearchModel> search(Map<String, String> loginMap) {

        MutableLiveData<SearchModel> responseModelMutableLiveData = new MutableLiveData<>();

        ApiClient.getAPIService().search(loginMap).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                if (response.isSuccessful()) {
                    responseModelMutableLiveData.postValue(response.body());
                } else {
                    response.body().setResult("Error");
                    responseModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
            }
        });
        return responseModelMutableLiveData;
    }


    public LiveData<IssueModel> issue(Map<String, String> loginMap) {

        MutableLiveData<IssueModel> responseModelMutableLiveData = new MutableLiveData<>();

        ApiClient.getAPIService().issue(loginMap).enqueue(new Callback<IssueModel>() {
            @Override
            public void onResponse(Call<IssueModel> call, Response<IssueModel> response) {
                if (response.isSuccessful()) {
                    responseModelMutableLiveData.postValue(response.body());
                } else {
                    response.body().setResult("Error");
                    responseModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<IssueModel> call, Throwable t) {
            }
        });
        return responseModelMutableLiveData;
    }



    public LiveData<UpdateModel> update(Map<String, String> loginMap) {

        MutableLiveData<UpdateModel> responseModelMutableLiveData = new MutableLiveData<>();

        ApiClient.getAPIService().update(loginMap).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                if (response.isSuccessful()) {
                    responseModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {
            }
        });
        return responseModelMutableLiveData;
    }


    public LiveData<StstusModel> getStatus() {
        MutableLiveData<StstusModel> productModelMutableLiveData = new MutableLiveData<StstusModel>();
        ApiClient.getAPIService().getStatus().enqueue(new Callback<StstusModel>() {
            @Override
            public void onResponse(Call<StstusModel> call, Response<StstusModel> response) {
                if (response.isSuccessful()) {
                    productModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<StstusModel> call, Throwable t) {
            }
        });
        return productModelMutableLiveData;
    }

}
