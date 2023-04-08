package com.inficta.alokitsupport.Act;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.inficta.alokitsupport.R;

public class Splace_Screan extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splace_screan);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splace_Screan.this, NewLoginDesign.class);
                startActivity(intent);
                Splace_Screan.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}