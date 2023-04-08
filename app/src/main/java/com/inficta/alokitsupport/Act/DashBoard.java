package com.inficta.alokitsupport.Act;

import static com.inficta.alokitsupport.Base.AppConstants.ENG_ID;
import static com.inficta.alokitsupport.Base.AppConstants.LOGIN_KEY;
import static com.inficta.alokitsupport.Base.AppConstants.USER_ID;
import static com.inficta.alokitsupport.Base.AppConstants.YEAR_ID;
import static com.inficta.alokitsupport.Base.AppConstants.YEAR_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.inficta.alokitsupport.Adapter.ExpireTimeAdapter;
import com.inficta.alokitsupport.Adapter.IssueAdapter;
import com.inficta.alokitsupport.Attendance.Attendance;
import com.inficta.alokitsupport.Base.Preferences;
import com.inficta.alokitsupport.Model.ExpiredDateTimeModel;
import com.inficta.alokitsupport.Model.IssueModel;
import com.inficta.alokitsupport.R;
import com.inficta.alokitsupport.Vm.CounterVm;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.iwgang.countdownview.CountdownView;

public class DashBoard extends AppCompatActivity implements ExpireTimeAdapter.OnItemClick{
    DrawerLayout drawerLayout;
    CountdownView countDownView;
    RecyclerView recExpiredTime;
    ActionBarDrawerToggle toggle;
    NavigationView nav;
    TextView textView;
    CounterVm counterVm;
    CardView cardSupport, cardLeave, cardAttendance, cardIssue;
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
        counterVm = new CounterVm(this.getApplication());
        preferences = new Preferences(this);
        textView = findViewById (R.id.textView);

        recExpiredTime = findViewById (R.id.recExpiredTime);
        recExpiredTime.setHasFixedSize(true);
        recExpiredTime.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("engineer_id", preferences.getStringPreference(ENG_ID));
        stringStringMap.put("year",  preferences.getStringPreference(YEAR_NAME));
        counterVm.expiredDateTime(stringStringMap).observe(DashBoard.this, new Observer<ExpiredDateTimeModel>() {
            @Override
            public void onChanged(ExpiredDateTimeModel expiredDateTimeModel) {
                if (expiredDateTimeModel.getResult().equals("1")){
                    recExpiredTime.setVisibility(View.VISIBLE);
                    ExpireTimeAdapter issueAdapter = new ExpireTimeAdapter(DashBoard.this, expiredDateTimeModel.getData(), DashBoard.this);
                    recExpiredTime.setAdapter(issueAdapter);
                }else {
                    recExpiredTime.setVisibility(View.VISIBLE);
                }
            }
        });


        drawerLayout = findViewById(R.id.drawer);
        nav = findViewById(R.id.navemenu);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (ActivityCompat.checkSelfPermission(
                DashBoard.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        else
            ActivityCompat.requestPermissions(DashBoard.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


        /*----------------------------------------------------- Navigation Drawer -------------------------------------------------------*/


        cardIssue = findViewById(R.id.cardIssue);
        cardIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this, Issue.class));
            }
        });

        cardAttendance = findViewById(R.id.cardAttendance);
        cardAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this, Attendance.class));
            }
        });

        cardLeave = findViewById(R.id.cardLeave);
        cardLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this, ApplyLeave.class));
            }
        });

        cardSupport = findViewById(R.id.cardSupport);
        cardSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this, Support.class));
            }
        });


        nav = findViewById(R.id.navemenu);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_Issue:
                        Intent intent2 = new Intent(DashBoard.this, Support.class);
                        startActivity(intent2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_Attendance:
                        Intent intent1 = new Intent(DashBoard.this, Attendance.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                        case R.id.nav_Leave:
                        Intent intent4 = new Intent(DashBoard.this, ApplyLeave.class);
                        startActivity(intent4);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_Profile:
                        Intent intent3 = new Intent(DashBoard.this, Profile.class);
                        startActivity(intent3);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_Support:
                        Intent intent = new Intent(DashBoard.this, Support.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_year:
                        Intent intent6 = new Intent(DashBoard.this, Year.class);
                        startActivity(intent6);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_logout:
                        preferences.deletePreference(LOGIN_KEY);
                        preferences.deletePreference(USER_ID);
                        startActivity(new Intent(DashBoard.this, NewLoginDesign.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;

                }
                return false;
            }
        });

    }

    @Override
    public void onClick(IssueModel.Datum mainCatModel) {

    }
}