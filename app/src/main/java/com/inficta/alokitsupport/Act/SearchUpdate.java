package com.inficta.alokitsupport.Act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.inficta.alokitsupport.Adapter.CustomSpinnerSelectStatus;
import com.inficta.alokitsupport.Base.BaseActivity;
import com.inficta.alokitsupport.Model.IssueModel;
import com.inficta.alokitsupport.Model.SearchModel;
import com.inficta.alokitsupport.Model.StstusModel;
import com.inficta.alokitsupport.Model.UpdateModel;
import com.inficta.alokitsupport.R;
import com.inficta.alokitsupport.Vm.IssueVm;
import com.inficta.alokitsupport.databinding.ActivityIssueUpdateBinding;
import com.inficta.alokitsupport.databinding.ActivitySearchUpdateBinding;

import java.util.HashMap;
import java.util.Map;

public class SearchUpdate extends BaseActivity {
    SearchModel.Datum datum;
    ActivitySearchUpdateBinding iu;
    int LocationId;
    IssueVm issueVm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_update);


        datum = (SearchModel.Datum) getIntent().getSerializableExtra("Sid");
        iu = ActivitySearchUpdateBinding.inflate(getLayoutInflater());
        setContentView(iu.getRoot());
        issueVm = new IssueVm(this.getApplication());

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

        issueVm.getStatus().observe(SearchUpdate.this, new Observer<StstusModel>() {
            @Override
            public void onChanged(StstusModel locationModel) {
                CustomSpinnerSelectStatus customSpinnerSelectState = new CustomSpinnerSelectStatus(SearchUpdate.this, locationModel.getData());
                iu.spnLocation.setAdapter(customSpinnerSelectState);
            }
        });

        iu.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> stringStringMap = new HashMap<>();
                progressDialog(SearchUpdate.this);
                stringStringMap.put("assets_issue_id", datum.getAssetsIssueId());
                stringStringMap.put("service_status", "" + LocationId);
                stringStringMap.put("remarks", iu.inputRemarks.getText().toString());
                issueVm.update(stringStringMap).observe(SearchUpdate.this, new Observer<UpdateModel>() {
                    @Override
                    public void onChanged(UpdateModel updateModel) {
                        if (updateModel.getResult().equals("1")) {
                            progressdialog.dismiss();
                            myToast(SearchUpdate.this, "Updated Success!");
                            finish();
                        } else {
                            progressdialog.dismiss();
                            myToast(SearchUpdate.this, "Updated Fail!");
                        }
                    }
                });
            }
        });
    }
}