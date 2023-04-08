package com.inficta.alokitsupport.Act;

import static com.inficta.alokitsupport.Base.AppConstants.YEAR_ID;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.lifecycle.Observer;

import com.inficta.alokitsupport.Adapter.CustomSpinnerSelectStatus;
import com.inficta.alokitsupport.Base.BaseActivity;
import com.inficta.alokitsupport.Base.Preferences;
import com.inficta.alokitsupport.Model.IssueModel;
import com.inficta.alokitsupport.Model.StstusModel;
import com.inficta.alokitsupport.Model.UpdateModel;
import com.inficta.alokitsupport.R;
import com.inficta.alokitsupport.Vm.IssueVm;
import com.inficta.alokitsupport.databinding.ActivityIssueUpdateBinding;

import java.util.HashMap;
import java.util.Map;

public class IssueUpdate extends BaseActivity {
    IssueModel.Datum datum;
    Preferences preferences;
    ActivityIssueUpdateBinding iu;
    int LocationId;
    IssueVm issueVm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_update);

        datum = (IssueModel.Datum) getIntent().getSerializableExtra("Iid");
        iu = ActivityIssueUpdateBinding.inflate(getLayoutInflater());
        setContentView(iu.getRoot());
        issueVm = new IssueVm(this.getApplication());
        preferences = new Preferences(this);
        iu.inputAid.setText(datum.getAsUnId());
        iu.inputAName.setText(datum.getAssetsName());
        iu.inputATyoe.setText(datum.getType());
        iu.inputId.setText(datum.getAssetsIssueId());
        iu.inputUid.setText(datum.getUserId());
        iu.inputUName.setText(datum.getUserName());
        iu.inputIssue.setText(datum.getIssue());
        iu.inputIssueDescription.setText(datum.getIssueDescription());
        iu.inputRemarks.setText(datum.getRemark());

        iu.spnLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                StstusModel.Datum selectedItem = (StstusModel.Datum) adapterView.getSelectedItem();
                LocationId = Integer.parseInt(selectedItem.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        issueVm.getStatus().observe(IssueUpdate.this, new Observer<StstusModel>() {
            @Override
            public void onChanged(StstusModel locationModel) {
                CustomSpinnerSelectStatus customSpinnerSelectState = new CustomSpinnerSelectStatus(IssueUpdate.this, locationModel.getData());
                iu.spnLocation.setAdapter(customSpinnerSelectState);
            }
        });

        iu.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> stringStringMap = new HashMap<>();
                progressDialog(IssueUpdate.this);
                stringStringMap.put("assets_issue_id", datum.getAssetsIssueId());
                stringStringMap.put("year", preferences.getStringPreference(YEAR_ID));
                stringStringMap.put("service_status", "" + LocationId);
                stringStringMap.put("remarks", iu.inputRemarks.getText().toString());
                issueVm.update(stringStringMap).observe(IssueUpdate.this, new Observer<UpdateModel>() {
                    @Override
                    public void onChanged(UpdateModel updateModel) {
                        if (updateModel.getResult().equals("1")) {
                            progressdialog.dismiss();
                            myToast(IssueUpdate.this, "Updated Success!");
                            finish();
                        } else {
                            progressdialog.dismiss();
                            myToast(IssueUpdate.this, "Updated Fail!");
                        }
                    }
                });
            }
        });
    }
}