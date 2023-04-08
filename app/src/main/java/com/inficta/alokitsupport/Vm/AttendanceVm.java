package com.inficta.alokitsupport.Vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.inficta.alokitsupport.Model.CompanyModel;
import com.inficta.alokitsupport.Model.InTimeModel;
import com.inficta.alokitsupport.Model.LeaveListModel;
import com.inficta.alokitsupport.Model.OutTimeModel;
import com.inficta.alokitsupport.Model.YearModel;
import com.inficta.alokitsupport.Repo.AttendanceRepo;

import java.util.Map;

public class AttendanceVm extends AndroidViewModel {
    AttendanceRepo attendanceRepo;

    public AttendanceVm(@NonNull Application application) {
        super(application);
        attendanceRepo = AttendanceRepo.getInstance();
    }

    public LiveData<InTimeModel> inTime(Map<String, String> loginMap) {
        return attendanceRepo.inTime(loginMap);
    }

    public LiveData<OutTimeModel> outTime(Map<String, String> loginMap) {
        return attendanceRepo.outTime(loginMap);
    }

    public LiveData<CompanyModel> getCompany() {
        return attendanceRepo.getCompany();

    }

     public LiveData<LeaveListModel> getLeaveList() {
        return attendanceRepo.getLeaveList();

    }


}


