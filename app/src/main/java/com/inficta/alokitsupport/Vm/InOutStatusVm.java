package com.inficta.alokitsupport.Vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.inficta.alokitsupport.Model.InOutStatusModel;
import com.inficta.alokitsupport.Repo.InOutStatusRepo;

import java.util.Map;

public class InOutStatusVm extends AndroidViewModel {
    InOutStatusRepo inOutStatusRepo;

    public InOutStatusVm(@NonNull Application application) {
        super(application);
        inOutStatusRepo = InOutStatusRepo.getInstance();
    }

    public LiveData<InOutStatusModel> inOutStatus(Map<String, String> loginMap) {
        return inOutStatusRepo.inOutStatus(loginMap);
    }
}


