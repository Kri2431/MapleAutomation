package com.inficta.alokitsupport.Vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.inficta.alokitsupport.Model.IssueModel;
import com.inficta.alokitsupport.Model.SearchModel;
import com.inficta.alokitsupport.Model.StstusModel;
import com.inficta.alokitsupport.Model.UpdateModel;
import com.inficta.alokitsupport.Repo.IssueRepo;

import java.util.Map;

public class IssueVm extends AndroidViewModel {
    IssueRepo issueRepo;

    public IssueVm(@NonNull Application application) {
        super(application);
        issueRepo = IssueRepo.getInstance();
    }

    public LiveData<SearchModel> search(Map<String, String> loginMap) {
        return issueRepo.search(loginMap);
    }

    public LiveData<IssueModel> issue(Map<String, String> loginMap) {
        return issueRepo.issue(loginMap);
    }

    public LiveData<UpdateModel> update(Map<String, String> loginMap) {
        return issueRepo.update(loginMap);
    }

    public LiveData<StstusModel> getStatus() {
        return issueRepo.getStatus();

    }
}