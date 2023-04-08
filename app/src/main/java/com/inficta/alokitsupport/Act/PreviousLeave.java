package com.inficta.alokitsupport.Act;

import static com.inficta.alokitsupport.Base.AppConstants.USER_ID;
import static com.inficta.alokitsupport.Base.AppConstants.YEAR_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.inficta.alokitsupport.Adapter.IssueAdapter;
import com.inficta.alokitsupport.Adapter.PreviousLeaveAdapter;
import com.inficta.alokitsupport.Adapter.SearchAdapter;
import com.inficta.alokitsupport.Base.BaseActivity;
import com.inficta.alokitsupport.Base.Preferences;
import com.inficta.alokitsupport.Model.IssueModel;
import com.inficta.alokitsupport.Model.PreviousLeaveModel;
import com.inficta.alokitsupport.Model.SearchModel;
import com.inficta.alokitsupport.R;
import com.inficta.alokitsupport.Vm.CounterVm;
import com.inficta.alokitsupport.databinding.ActivityPrevioursLeaveBinding;

import java.util.HashMap;
import java.util.Map;

public class PreviousLeave extends BaseActivity implements IssueAdapter.OnItemClick, SearchAdapter.OnItemClick {

    ActivityPrevioursLeaveBinding is;
    Preferences preferences;
    CounterVm counterVm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previours_leave);

        is = ActivityPrevioursLeaveBinding.inflate(getLayoutInflater());
        setContentView(is.getRoot());

        preferences = new Preferences(this);
        counterVm = new CounterVm(this.getApplication());
        is.recIssue.setHasFixedSize(true);
        is.recIssue.setLayoutManager(new LinearLayoutManager(PreviousLeave.this));
        Map<String, String> stringStringMap = new HashMap<>();
        progressDialog(PreviousLeave.this);
        stringStringMap.put("user_id", preferences.getStringPreference(USER_ID));
        stringStringMap.put("year", preferences.getStringPreference(YEAR_ID));
        counterVm.leaveList(stringStringMap).observe(PreviousLeave.this, new Observer<PreviousLeaveModel>() {
            @Override
            public void onChanged(PreviousLeaveModel issueModel) {
                if (issueModel.getResult().equals("1")) {
                    progressdialog.dismiss();
                    is.recIssue.setVisibility(View.VISIBLE);
                    PreviousLeaveAdapter issueAdapter = new PreviousLeaveAdapter(PreviousLeave.this, issueModel.getData());
                    is.recIssue.setAdapter(issueAdapter);
                } else {
                    progressdialog.dismiss();
                    is.empty.setVisibility(View.VISIBLE);
                }
            }
        });


        is.edSerch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(is.edSerch.getText())) {
                    is.recSearch.setHasFixedSize(true);
                    is.recSearch.setLayoutManager(new LinearLayoutManager(PreviousLeave.this));
                    progressDialog(PreviousLeave.this);
                    stringStringMap.put("user_id", preferences.getStringPreference(USER_ID));
                    stringStringMap.put("year", preferences.getStringPreference(YEAR_ID));
                    counterVm.leaveList(stringStringMap).observe(PreviousLeave.this, new Observer<PreviousLeaveModel>() {
                        @Override
                        public void onChanged(PreviousLeaveModel issueModel) {
                            if (issueModel.getResult().equals("1")) {
                                progressdialog.dismiss();
                                is.recSearch.setVisibility(View.VISIBLE);
                                PreviousLeaveAdapter issueAdapter = new PreviousLeaveAdapter(PreviousLeave.this, issueModel.getData());
                                is.recSearch.setAdapter(issueAdapter);
                            } else {
                                progressdialog.dismiss();
                                is.empty.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        is.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is.relativeLayout1.setVisibility(View.VISIBLE);
                is.relativeLayout.setVisibility(View.GONE);
                is.recIssue.setVisibility(View.GONE);
            }
        });

        is.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        is.back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is.relativeLayout1.setVisibility(View.GONE);
                is.relativeLayout.setVisibility(View.VISIBLE);
                is.recIssue.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(IssueModel.Datum mainCatModel) {
        Intent intent = new Intent(PreviousLeave.this, IssueUpdate.class);
        intent.putExtra("Iid", mainCatModel);
        startActivity(intent);
    }

    @Override
    public void onClick(SearchModel.Datum mainCatModel) {
        Intent intent = new Intent(PreviousLeave.this, SearchUpdate.class);
        intent.putExtra("Sid", mainCatModel);
        startActivity(intent);
    }
}