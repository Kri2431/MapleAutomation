package com.inficta.alokitsupport.Act;

import static com.inficta.alokitsupport.Base.AppConstants.LOGIN_KEY;
import static com.inficta.alokitsupport.Base.AppConstants.SAVE_SESSION_VALUE;
import static com.inficta.alokitsupport.Base.AppConstants.SAVE_YEAR_SESSION_VALUE;
import static com.inficta.alokitsupport.Base.AppConstants.YEAR_ID;
import static com.inficta.alokitsupport.Base.AppConstants.YEAR_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.inficta.alokitsupport.Adapter.CustomSpinnerSelectYear;
import com.inficta.alokitsupport.Base.BaseActivity;
import com.inficta.alokitsupport.Base.Preferences;
import com.inficta.alokitsupport.Model.YearModel;
import com.inficta.alokitsupport.R;
import com.inficta.alokitsupport.Vm.LoginVm;
import com.inficta.alokitsupport.databinding.ActivityYearBinding;

public class Year extends BaseActivity {
    ActivityYearBinding yr;
    Preferences preferences;
    boolean savePswD = false;
    int yearId;
    LoginVm loginVm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);

        yr = ActivityYearBinding.inflate(getLayoutInflater());
        setContentView(yr.getRoot());
        loginVm = new LoginVm(this.getApplication());
        preferences = new Preferences(this);

        yr.spnYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                YearModel.Datum selectedItem = (YearModel.Datum) adapterView.getSelectedItem();
                yearId = Integer.parseInt(selectedItem.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        loginVm.getYear().observe(Year.this, new Observer<YearModel>() {
            @Override
            public void onChanged(YearModel yearModel) {
                CustomSpinnerSelectYear customSpinnerSelectYear = new CustomSpinnerSelectYear(Year.this, yearModel.getData());
                yr.spnYear.setAdapter(customSpinnerSelectYear);
            }
        });

        yr.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yearId == 0) {
                    myToast(Year.this, "Please select Location.");
                } else {
                    preferences.putPrefString(YEAR_ID, "" + yearId);
                    startActivity(new Intent(Year.this, DashBoard.class));
                    finish();
                }
            }
        });

    }

}