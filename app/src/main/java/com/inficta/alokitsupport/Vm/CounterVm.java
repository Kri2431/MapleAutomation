package com.inficta.alokitsupport.Vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.inficta.alokitsupport.Model.AttendanceReportModel;
import com.inficta.alokitsupport.Model.CounterModel;
import com.inficta.alokitsupport.Model.ExpiredDateTimeModel;
import com.inficta.alokitsupport.Model.LeaveModel;
import com.inficta.alokitsupport.Model.PreviousLeaveModel;
import com.inficta.alokitsupport.Model.ProfileModel;
import com.inficta.alokitsupport.Model.SuportModel;
import com.inficta.alokitsupport.Repo.CounterRepo;

import java.util.Map;

public class CounterVm extends AndroidViewModel {
    CounterRepo counterRepo;

    public CounterVm(@NonNull Application application) {
        super(application);
        counterRepo = CounterRepo.getInstance();
    }

    public LiveData<CounterModel> counter(Map<String, String> loginMap) {
        return counterRepo.counter(loginMap);
    }

    public LiveData<SuportModel> support(Map<String, String> loginMap) {
        return counterRepo.support(loginMap);
    }

    public LiveData<ProfileModel> profile(Map<String, String> loginMap) {
        return counterRepo.profile(loginMap);
    }

    public LiveData<ExpiredDateTimeModel> expiredDateTime(Map<String, String> loginMap) {
        return counterRepo.expiredDateTime(loginMap);
    }

    public LiveData<LeaveModel> applyLeave(Map<String, String> loginMap) {
        return counterRepo.applyLeave(loginMap);
    }

    public LiveData<AttendanceReportModel> aReport(Map<String, String> loginMap) {
        return counterRepo.aReport(loginMap);
    }

    public LiveData<PreviousLeaveModel> leaveList(Map<String, String> loginMap) {
        return counterRepo.leaveList(loginMap);
    }
}


