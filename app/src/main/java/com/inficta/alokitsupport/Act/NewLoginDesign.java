package com.inficta.alokitsupport.Act;

import static com.inficta.alokitsupport.Base.AppConstants.ENG_ID;
import static com.inficta.alokitsupport.Base.AppConstants.LOGIN_KEY;
import static com.inficta.alokitsupport.Base.AppConstants.MOB_NUMBER;
import static com.inficta.alokitsupport.Base.AppConstants.SAVE_SESSION_VALUE;
import static com.inficta.alokitsupport.Base.AppConstants.USER_ID;
import static com.inficta.alokitsupport.Base.AppConstants.USER_NAME;
import static com.inficta.alokitsupport.Base.AppConstants.YEAR_ID;
import static com.inficta.alokitsupport.Base.AppConstants.YEAR_NAME;
import static com.inficta.alokitsupport.Base.Util.isEmptyOrNullArray;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.inficta.alokitsupport.Adapter.CustomSpinnerSelectCompany;
import com.inficta.alokitsupport.Adapter.CustomSpinnerSelectYear;
import com.inficta.alokitsupport.Attendance.Attendance;
import com.inficta.alokitsupport.Base.BaseActivity;
import com.inficta.alokitsupport.Base.Preferences;
import com.inficta.alokitsupport.Model.CompanyModel;
import com.inficta.alokitsupport.Model.LoginModel;
import com.inficta.alokitsupport.Model.YearModel;
import com.inficta.alokitsupport.R;
import com.inficta.alokitsupport.Vm.LoginVm;
import com.inficta.alokitsupport.databinding.ActivityNewLoginDesignBinding;

import java.util.HashMap;
import java.util.Map;

public class NewLoginDesign extends BaseActivity {
    ActivityNewLoginDesignBinding nd;
    LoginVm loginVm;
    boolean savePswD = false;
    Preferences preferences;
    String deviceId;
    int yearId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login_design);

        nd = ActivityNewLoginDesignBinding.inflate(getLayoutInflater());
        setContentView(nd.getRoot());
        preferences = new Preferences(this);

       /* nd.singUp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
              nd.singUp.setBackgroundResource(R.drawable.switch_trcks);
                nd.singUp.setTextColor(getColor(R.color.textColor));
                nd.logIn.setBackground(null);
                nd.singUpLayout.setVisibility(View.VISIBLE);
                nd.logInLayout.setVisibility(View.GONE);
                nd.logIn.setTextColor(getColor(R.color.pinkColor));
                nd.LogIn.setVisibility(View.GONE);
                nd.SingIn.setVisibility(View.VISIBLE);

            }
        });*/

        loginVm = new LoginVm(this.getApplication());


        String sessionValue = preferences.getStringPreference(LOGIN_KEY);
        if (sessionValue != null) {
            Intent intent = new Intent(NewLoginDesign.this, DashBoard.class);
            startActivity(intent);
            finish();
        } else {
            allocationMemory();
        }

        final String[] androidId = {Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)};
        deviceId = androidId[0].toString();
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            String token = FirebaseInstanceId.getInstance().getToken();
                            return;
                        }
                    }
                });

        nd.deviceId.setText(deviceId);

        nd.spnYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                YearModel.Datum selectedItem = (YearModel.Datum) adapterView.getSelectedItem();
                yearId = Integer.parseInt(selectedItem.getId());
                String year = selectedItem.getYear();
                preferences.putPrefString(YEAR_NAME, year);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        loginVm.getYear().observe(NewLoginDesign.this, new Observer<YearModel>() {
            @Override
            public void onChanged(YearModel yearModel) {
                CustomSpinnerSelectYear customSpinnerSelectYear = new CustomSpinnerSelectYear(NewLoginDesign.this, yearModel.getData());
                nd.spnYear.setAdapter(customSpinnerSelectYear);
            }
        });

        nd.LogIn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                /*nd.singUp.setBackground(null);
                nd.singUp.setTextColor(getColor(R.color.pinkColor));
                nd.logIn.setBackgroundResource(R.drawable.switch_trcks);
                nd.singUpLayout.setVisibility(View.GONE);
                nd.logInLayout.setVisibility(View.VISIBLE);
                nd.logIn.setTextColor(getColor(R.color.textColor));
                nd.LogIn.setVisibility(View.VISIBLE);
                nd.SingIn.setVisibility(View.GONE);*/

                progressDialog(NewLoginDesign.this);
                boolean isEmpty = isEmptyOrNullArray(new String[]{nd.eMail.getText().toString(), nd.passwords.getText().toString()});
                if (isEmpty) {
                    if (yearId == 0) {
                        progressdialog.dismiss();
                        myToast(NewLoginDesign.this, "Please select year!");
                    } else {
                        Map<String, String> stringStringMap = new HashMap<>();
                        String token = FirebaseInstanceId.getInstance().getToken();
                        stringStringMap.put("token_id", token);
                        stringStringMap.put("device_id", deviceId);
                        stringStringMap.put("user_id", nd.eMail.getText().toString());
                        stringStringMap.put("password", nd.passwords.getText().toString());
                        loginVm.login(stringStringMap).observe(NewLoginDesign.this, new Observer<LoginModel>() {
                            @Override
                            public void onChanged(LoginModel loginModel) {
                                if (loginModel.getResult().equals("1")) {
                                    progressdialog.dismiss();
                                    savePswD = true;
                                    if (savePswD)
                                        preferences.putPrefString(LOGIN_KEY, SAVE_SESSION_VALUE);
                                    preferences.putPrefString(USER_ID, loginModel.getData().get(0).getUserId());
                                    preferences.putPrefString(ENG_ID, loginModel.getData().get(0).getEngineerId());
                                    preferences.putPrefString(USER_NAME, loginModel.getData().get(0).getName());
                                    preferences.putPrefString(MOB_NUMBER, loginModel.getData().get(0).getContactNo());
                                    preferences.putPrefString(YEAR_ID, "" + yearId);
                                    startActivity(new Intent(NewLoginDesign.this, DashBoard.class));
                                    myToast(NewLoginDesign.this, "Login successfully!");
                                } else {
                                    progressdialog.dismiss();
                                    myToast(NewLoginDesign.this, "Please enter valid userid & password!");
                                }
                            }
                        });
                    }
                } else {
                    progressdialog.dismiss();
                    Toast.makeText(NewLoginDesign.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void allocationMemory() {
        savePswD = true;
    }
}