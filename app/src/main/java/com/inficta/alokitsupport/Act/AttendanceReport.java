package com.inficta.alokitsupport.Act;

import static com.inficta.alokitsupport.Base.AppConstants.USER_ID;
import static com.inficta.alokitsupport.Base.AppConstants.YEAR_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.inficta.alokitsupport.Adapter.AttendanceReportAdapter;
import com.inficta.alokitsupport.Adapter.CustomSpinnerSelectCompany;
import com.inficta.alokitsupport.Adapter.CustomSpinnerSelectYear;
import com.inficta.alokitsupport.Adapter.IssueAdapter;
import com.inficta.alokitsupport.Attendance.Attendance;
import com.inficta.alokitsupport.Base.BaseActivity;
import com.inficta.alokitsupport.Base.Preferences;
import com.inficta.alokitsupport.Model.AttendanceReportModel;
import com.inficta.alokitsupport.Model.CompanyModel;
import com.inficta.alokitsupport.Model.IssueModel;
import com.inficta.alokitsupport.Model.YearModel;
import com.inficta.alokitsupport.R;
import com.inficta.alokitsupport.Vm.AttendanceVm;
import com.inficta.alokitsupport.Vm.CounterVm;
import com.inficta.alokitsupport.Vm.IssueVm;
import com.inficta.alokitsupport.Vm.LoginVm;
import com.inficta.alokitsupport.databinding.ActivityAttedanceReportBinding;

import java.util.HashMap;
import java.util.Map;

public class AttendanceReport extends BaseActivity {
    int Type;
    String year;
    LoginVm loginVm;
    ActivityAttedanceReportBinding ar;
    CounterVm counterVm;
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attedance_report);
        counterVm = new CounterVm(this.getApplication());
        ar = ActivityAttedanceReportBinding.inflate(getLayoutInflater());
        setContentView(ar.getRoot());
        preferences = new Preferences(this);
        loginVm = new LoginVm(this.getApplication());
        ar.month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] type = {"January", "February", "March","April", "May", "june","July", "August", "September","October", "November", "December"};
                new MaterialAlertDialogBuilder(AttendanceReport.this, R.style.AlertDialogTheme)
                        .setTitle("Select Month")
                        .setItems(type, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if ("January".equals(type[which])) {
                                    Type = 1;
                                    ar.month.setText("January");
                                } else if ("February".equals(type[which])) {
                                    Type = 2;
                                    ar.month.setText("February");

                                } else if ("March".equals(type[which])) {
                                    Type = 3;
                                    ar.month.setText("March");
                                } else if ("April".equals(type[which])) {
                                    Type = 4;
                                    ar.month.setText("April");

                                } else if ("May".equals(type[which])) {
                                    Type = 5;
                                    ar.month.setText("May");
                                }else if ("june".equals(type[which])) {
                                    Type = 6;
                                    ar.month.setText("june");

                                } else if ("July".equals(type[which])) {
                                    Type = 7;
                                    ar.month.setText("July");
                                } else if ("August".equals(type[which])) {
                                    Type = 8;
                                    ar.month.setText("August");

                                } else if ("September".equals(type[which])) {
                                    Type = 9;
                                    ar.month.setText("September");
                                }else if ("October".equals(type[which])) {
                                    Type = 10;
                                    ar.month.setText("October");

                                } else if ("November".equals(type[which])) {
                                    Type = 11;
                                    ar.month.setText("November");
                                } else if ("December".equals(type[which])) {
                                    Type = 12;
                                    ar.month.setText("December");

                                }
                            }
                        })
                        .show();
            }
        });

        ar.spnYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                YearModel.Datum selectedItem = (YearModel.Datum) adapterView.getSelectedItem();
                //year = selectedItem.getCompanyName();
                year = selectedItem.getYear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



        loginVm.getYear().observe(AttendanceReport.this, new Observer<YearModel>() {
            @Override
            public void onChanged(YearModel yearModel) {
                CustomSpinnerSelectYear customSpinnerSelectYear = new CustomSpinnerSelectYear(AttendanceReport.this, yearModel.getData());
                ar.spnYear.setAdapter(customSpinnerSelectYear);
            }
        });

        ar.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ar.recAttendance.setHasFixedSize(true);
                ar.recAttendance.setLayoutManager(new LinearLayoutManager(AttendanceReport.this));
                Map<String, String> stringStringMap = new HashMap<>();
                progressDialog(AttendanceReport.this);
                stringStringMap.put("engineer_id",preferences.getStringPreference(USER_ID));
                stringStringMap.put("attdance_month", "" + Type);
                    stringStringMap.put("year", year);
                counterVm.aReport(stringStringMap).observe(AttendanceReport.this, new Observer<AttendanceReportModel>() {
                    @Override
                    public void onChanged(AttendanceReportModel issueModel) {
                        if (issueModel.getResult().equals("1")) {
                            progressdialog.dismiss();
                            ar.recAttendance.setVisibility(View.VISIBLE);
                            ar.lAttendance.setVisibility(View.VISIBLE);
                            AttendanceReportAdapter issueAdapter = new AttendanceReportAdapter(AttendanceReport.this, issueModel.getData());
                            ar.recAttendance.setAdapter(issueAdapter);
                        } else {
                            progressdialog.dismiss();
                            ar.recAttendance.setVisibility(View.GONE);
                            ar.lAttendance.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
    }
}