package com.inficta.alokitsupport.Act;

import static com.inficta.alokitsupport.Base.AppConstants.USER_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;

import com.inficta.alokitsupport.Base.BaseActivity;
import com.inficta.alokitsupport.Base.Preferences;
import com.inficta.alokitsupport.Model.ProfileModel;
import com.inficta.alokitsupport.R;
import com.inficta.alokitsupport.Vm.CounterVm;
import com.inficta.alokitsupport.databinding.ActivityProfileBinding;

import java.util.HashMap;
import java.util.Map;

public class Profile extends BaseActivity {

    ActivityProfileBinding pf;
    CounterVm counterVm;
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pf = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(pf.getRoot());
        counterVm = new CounterVm(this.getApplication());
        preferences = new Preferences(this);

        Map<String, String> stringStringMap = new HashMap<>();
        progressDialog(Profile.this);
        stringStringMap.put("engineer_id", preferences.getStringPreference(USER_ID));
        counterVm.profile(stringStringMap).observe(Profile.this, new Observer<ProfileModel>() {
            @Override
            public void onChanged(ProfileModel profileModel) {
                if (profileModel.getResult().equals("1")) {
                    progressdialog.dismiss();
                    pf.uName.setText(profileModel.getData().get(0).getName());
                    pf.inputName.setText(profileModel.getData().get(0).getName());
                    pf.inputMobile.setText(profileModel.getData().get(0).getContactNo());
                    pf.inputEmail.setText(profileModel.getData().get(0).getEmail());
                } else {
                    progressdialog.dismiss();
                }
            }
        });
        pf.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}