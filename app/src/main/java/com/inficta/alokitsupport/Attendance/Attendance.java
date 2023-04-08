package com.inficta.alokitsupport.Attendance;

import static com.inficta.alokitsupport.Base.AppConstants.IN_DATE;
import static com.inficta.alokitsupport.Base.AppConstants.IN_TIME;
import static com.inficta.alokitsupport.Base.AppConstants.USER_ID;
import static com.inficta.alokitsupport.Base.AppConstants.USER_NAME;
import static com.inficta.alokitsupport.Base.AppConstants.YEAR_ID;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.inficta.alokitsupport.Act.AttendanceReport;
import com.inficta.alokitsupport.Adapter.CustomSpinnerSelectCompany;
import com.inficta.alokitsupport.Base.BaseActivity;
import com.inficta.alokitsupport.Base.Preferences;
import com.inficta.alokitsupport.Model.CompanyModel;
import com.inficta.alokitsupport.Model.InOutStatusModel;
import com.inficta.alokitsupport.Model.InTimeModel;
import com.inficta.alokitsupport.Model.OutTimeModel;
import com.inficta.alokitsupport.R;
import com.inficta.alokitsupport.Vm.AttendanceVm;
import com.inficta.alokitsupport.Vm.InOutStatusVm;
import com.inficta.alokitsupport.databinding.ActivityAttendanceBinding;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Attendance extends BaseActivity {
    @TargetApi(Build.VERSION_CODES.O)
    ActivityAttendanceBinding at;
    AttendanceVm attendanceVm;
    InOutStatusVm inOutStatusVm;
    Preferences preferences;
    FusedLocationProviderClient fusedLocationProviderClient;
    int CompanyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        at = ActivityAttendanceBinding.inflate(getLayoutInflater());
        setContentView(at.getRoot());
        preferences = new Preferences(this);
        attendanceVm = new AttendanceVm(this.getApplication());
        inOutStatusVm = new InOutStatusVm(this.getApplication());

        at.aReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Attendance.this, AttendanceReport.class));
            }
        });

        Map<String, String> stringStringMap = new HashMap<>();
        progressDialog(Attendance.this);
        stringStringMap.put("user_id", preferences.getStringPreference(USER_ID));
        stringStringMap.put("year", preferences.getStringPreference(YEAR_ID));
        inOutStatusVm.inOutStatus(stringStringMap).observe(Attendance.this, new Observer<InOutStatusModel>() {
            @Override
            public void onChanged(InOutStatusModel inOutStatusModel) {
                if (inOutStatusModel.getResult().equals("1")) {
                    progressdialog.dismiss();
                    if (inOutStatusModel.getData().get(0).getInOutStatus().equals("1")) {
                        progressdialog.dismiss();
                        at.InTime.setVisibility(View.GONE);
                        at.OutTime.setVisibility(View.VISIBLE);
                        at.InOutDate.setText(preferences.getStringPreference(IN_DATE));
                        at.txtInTime.setText(preferences.getStringPreference(IN_TIME));
                    } else {
                        progressdialog.dismiss();
                        at.InTime.setVisibility(View.VISIBLE);
                        at.OutTime.setVisibility(View.GONE);
                        preferences.deletePreference(IN_DATE);
                        preferences.deletePreference(IN_TIME);
                    }
                } else {
                    progressdialog.dismiss();
                }
            }
        });

        at.spnCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                CompanyModel.Datum selectedItem = (CompanyModel.Datum) adapterView.getSelectedItem();
                CompanyId = Integer.parseInt(selectedItem.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        attendanceVm.getCompany().observe(Attendance.this, new Observer<CompanyModel>() {
            @Override
            public void onChanged(CompanyModel companyModel) {
                CustomSpinnerSelectCompany customSpinnerSelectState = new CustomSpinnerSelectCompany(Attendance.this, companyModel.getData());
                at.spnCompany.setAdapter(customSpinnerSelectState);
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        at.uName.setText(preferences.getStringPreference(USER_NAME));

        at.InTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(
                        Attendance.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    inTime();
                else
                    ActivityCompat.requestPermissions(Attendance.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        });

        at.OutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(
                        Attendance.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    outTime();
                else
                    ActivityCompat.requestPermissions(Attendance.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }
        });

        at.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void outTime() {
        if (CompanyId == 0) {
            progressdialog.dismiss();
            myToast(Attendance.this, "Please select Plant.");
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(Attendance.this);
            dialog.setCancelable(false);
            dialog.setTitle("Attendance Conformation");
            dialog.setMessage("Do you want to register this attendance?");
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    showLocationList();
                    Map<String, String> stringStringMap = new HashMap<>();
                    progressDialog(Attendance.this);
                    stringStringMap.put("user_id", preferences.getStringPreference(USER_ID));
                    stringStringMap.put("out", "Out");
                    stringStringMap.put("year", preferences.getStringPreference(YEAR_ID));
                    stringStringMap.put("company_out_id", "" + CompanyId);
                    stringStringMap.put("latitude", at.tvLat.getText().toString());
                    stringStringMap.put("longitude", at.tvLong.getText().toString());
                    attendanceVm.outTime(stringStringMap).observe(Attendance.this, new Observer<OutTimeModel>() {
                        @Override
                        public void onChanged(OutTimeModel outTimeModel) {
                            if (outTimeModel.getResult().equals("1")) {
                                progressdialog.dismiss();
                                at.InTime.setVisibility(View.VISIBLE);
                                at.OutTime.setVisibility(View.GONE);

                                at.OutDate.setText(outTimeModel.getData().get(0).getDate());
                                at.txtOutTime.setText(outTimeModel.getData().get(0).getTime());
                                myToast(Attendance.this, "Your attendance for sign out has been submitted! Thank you!");
                            } else if (outTimeModel.getResult().equals("2")) {
                                progressdialog.dismiss();
                                myToast(Attendance.this, "Sorry! you ae outside from location. you can't submit attendance!");
                            }else if (outTimeModel.getResult().equals("3")) {
                                progressdialog.dismiss();
                                myToast(Attendance.this, "Please select current year!");
                            } else {
                                progressdialog.dismiss();
                                myToast(Attendance.this, "Something Wrong");
                            }
                        }
                    });
                }
            })
                    .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            final AlertDialog alert = dialog.create();
            alert.show();
        }
    }

    private void inTime() {
        if (CompanyId == 0) {
            progressdialog.dismiss();
            myToast(Attendance.this, "Please select Plant.");
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(Attendance.this);
            dialog.setCancelable(false);
            dialog.setTitle("Attendance Conformation");
            dialog.setMessage("Do you want to register this attendance?");
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    showLocationList();
                    Map<String, String> stringStringMap = new HashMap<>();
                    progressDialog(Attendance.this);
                    stringStringMap.put("user_id", preferences.getStringPreference(USER_ID));
                    stringStringMap.put("in", "In");
                    stringStringMap.put("year", preferences.getStringPreference(YEAR_ID));
                    stringStringMap.put("company_in_id", "" + CompanyId);
                    stringStringMap.put("latitude", at.tvLat.getText().toString());
                    stringStringMap.put("longitude", at.tvLong.getText().toString());
                    attendanceVm.inTime(stringStringMap).observe(Attendance.this, new Observer<InTimeModel>() {
                        @Override
                        public void onChanged(InTimeModel inTimeModel) {
                            if (inTimeModel.getResult().equals("1")) {
                                progressdialog.dismiss();
                                at.InTime.setVisibility(View.GONE);
                                at.OutTime.setVisibility(View.VISIBLE);

                                preferences.putPrefString(IN_DATE, inTimeModel.getData().get(0).getDate());
                                preferences.putPrefString(IN_TIME, inTimeModel.getData().get(0).getTime());

                                at.InOutDate.setText(inTimeModel.getData().get(0).getDate());
                                at.txtInTime.setText(inTimeModel.getData().get(0).getTime());
                                myToast(Attendance.this, "Your attendance for sign in has been submitted! Thank you!");
                            } else if (inTimeModel.getResult().equals("2")) {
                                progressdialog.dismiss();
                                myToast(Attendance.this, "Sorry! you ae outside from location. you can't submit attendance!");
                            }else if (inTimeModel.getResult().equals("3")) {
                                progressdialog.dismiss();
                                myToast(Attendance.this, "Please select current year!");
                            } else {
                                progressdialog.dismiss();
                                myToast(Attendance.this, "Something Wrong");
                            }
                        }
                    });
                }
            })
                    .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            final AlertDialog alert = dialog.create();
            alert.show();
        }
    }

    @SuppressLint("MissingPermission")
    private void showLocationList() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(Attendance.this, Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        at.tvLat.setText("" + addressList.get(0).getLatitude());
                        at.tvLong.setText("" + addressList.get(0).getLongitude());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Attendance.this, "Permission denied!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
