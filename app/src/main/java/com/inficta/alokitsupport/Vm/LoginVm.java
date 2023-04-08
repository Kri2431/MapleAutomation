package com.inficta.alokitsupport.Vm;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.inficta.alokitsupport.Model.LoginModel;
import com.inficta.alokitsupport.Model.YearModel;
import com.inficta.alokitsupport.Repo.LoginRepo;

import java.util.Map;

import androidx.annotation.NonNull;

public class LoginVm extends AndroidViewModel {
    LoginRepo loginRepository;

    public LoginVm(@NonNull Application application) {
        super(application);
        loginRepository = LoginRepo.getInstance();
    }

    public LiveData<LoginModel> login(Map<String, String> loginMap) {
        return loginRepository.login(loginMap);
    }

    public LiveData<YearModel> getYear() {
        return loginRepository.getYear();

    }

}


