package com.inficta.alokitsupport.Act;

import static com.inficta.alokitsupport.Base.AppConstants.ENG_ID;
import static com.inficta.alokitsupport.Base.AppConstants.USER_ID;
import static com.inficta.alokitsupport.Base.AppConstants.YEAR_ID;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.inficta.alokitsupport.Adapter.IssueAdapter;
import com.inficta.alokitsupport.Adapter.SearchAdapter;
import com.inficta.alokitsupport.Base.BaseActivity;
import com.inficta.alokitsupport.Base.Preferences;
import com.inficta.alokitsupport.Model.IssueModel;
import com.inficta.alokitsupport.Model.SearchModel;
import com.inficta.alokitsupport.R;
import com.inficta.alokitsupport.Vm.IssueVm;
import com.inficta.alokitsupport.databinding.ActivityIssueBinding;

import java.util.HashMap;
import java.util.Map;

public class Issue extends BaseActivity implements IssueAdapter.OnItemClick, SearchAdapter.OnItemClick {

    ActivityIssueBinding is;
    Preferences preferences;
    IssueVm issueVm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        is = ActivityIssueBinding.inflate(getLayoutInflater());
        setContentView(is.getRoot());

        preferences = new Preferences(this);
        issueVm = new IssueVm(this.getApplication());
        is.recIssue.setHasFixedSize(true);
        is.recIssue.setLayoutManager(new LinearLayoutManager(Issue.this));
        Map<String, String> stringStringMap = new HashMap<>();
        progressDialog(Issue.this);
        stringStringMap.put("engineer_user_id", preferences.getStringPreference(ENG_ID));
        stringStringMap.put("year", preferences.getStringPreference(YEAR_ID));
        issueVm.issue(stringStringMap).observe(Issue.this, new Observer<IssueModel>() {
            @Override
            public void onChanged(IssueModel issueModel) {
                if (issueModel.getResult().equals("1")) {
                    progressdialog.dismiss();
                    is.recIssue.setVisibility(View.VISIBLE);
                    IssueAdapter issueAdapter = new IssueAdapter(Issue.this, issueModel.getData(), Issue.this);
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
                    is.recSearch.setLayoutManager(new LinearLayoutManager(Issue.this));
                    stringStringMap.put("assets_issue_id", is.edSerch.getText().toString());
                    stringStringMap.put("year", preferences.getStringPreference(YEAR_ID));
                    stringStringMap.put("user_id", is.edSerch.getText().toString());
                    issueVm.search(stringStringMap).observe(Issue.this, new Observer<SearchModel>() {
                        @Override
                        public void onChanged(SearchModel issueModel) {
                            if (issueModel.getResult().equals("1")) {
                                is.recSearch.setVisibility(View.VISIBLE);
                                SearchAdapter issueAdapter = new SearchAdapter(Issue.this, issueModel.getData(), Issue.this);
                                is.recSearch.setAdapter(issueAdapter);
                            } else {
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
        Intent intent = new Intent(Issue.this, IssueUpdate.class);
        intent.putExtra("Iid", mainCatModel);
        startActivity(intent);
    }

    @Override
    public void onClick(SearchModel.Datum mainCatModel) {
        Intent intent = new Intent(Issue.this, SearchUpdate.class);
        intent.putExtra("Sid", mainCatModel);
        startActivity(intent);
    }
}