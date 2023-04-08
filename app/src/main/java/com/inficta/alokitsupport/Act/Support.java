package com.inficta.alokitsupport.Act;

import static com.inficta.alokitsupport.Base.AppConstants.USER_ID;
import static com.inficta.alokitsupport.Base.AppConstants.YEAR_ID;
import static com.inficta.alokitsupport.Base.Util.isEmptyOrNullArray;

import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import com.inficta.alokitsupport.Base.BaseActivity;
import com.inficta.alokitsupport.Base.Preferences;
import com.inficta.alokitsupport.Model.SuportModel;
import com.inficta.alokitsupport.R;
import com.inficta.alokitsupport.Vm.CounterVm;
import com.inficta.alokitsupport.databinding.ActivitySupportBinding;

import java.util.HashMap;
import java.util.Map;

public class Support extends BaseActivity {

    ActivitySupportBinding sp;
    CounterVm counterVm;
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        sp = ActivitySupportBinding.inflate(getLayoutInflater());
        setContentView(sp.getRoot());

        counterVm = new CounterVm(this.getApplication());
        preferences = new Preferences(this);
        sp.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sp.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isEmpty = isEmptyOrNullArray(new String[]{sp.inputMobile.getText().toString(), sp.inputName.getText().toString(), sp.inputFeedback.getText().toString()});
                if (isEmpty) {
                    if (Patterns.EMAIL_ADDRESS.matcher(sp.inputEmail.getText().toString()).matches()) {
                        Map<String, String> stringStringMap = new HashMap<>();
                        progressDialog(Support.this);
                        stringStringMap.put("engineer_id", preferences.getStringPreference(USER_ID));
                        stringStringMap.put("name", sp.inputName.getText().toString());
                        stringStringMap.put("year", preferences.getStringPreference(YEAR_ID));
                        stringStringMap.put("contact_no", sp.inputMobile.getText().toString());
                        stringStringMap.put("email", sp.inputEmail.getText().toString());
                        stringStringMap.put("message", sp.inputFeedback.getText().toString());
                        counterVm.support(stringStringMap).observe(Support.this, new Observer<SuportModel>() {
                            @Override
                            public void onChanged(SuportModel counterModel) {
                                if (counterModel.getResult().equals("1")) {
                                    progressdialog.dismiss();
                                    myToast(Support.this, "Thank you. Query has been submitted to helpdesk!");
                                    finish();
                                } else {
                                    progressdialog.dismiss();
                                }

                            }
                        });
                    } else {
                        myToast(Support.this, "Enter valid Email address!");
                    }
                } else {
                    myToast(Support.this, "All fields are mandatory");
                }
            }
        });
    }
}