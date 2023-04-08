package com.inficta.alokitsupport.Base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.github.ybq.android.spinkit.style.FadingCircle;
import com.inficta.alokitsupport.R;

import java.util.Timer;

public abstract class BaseActivity extends AppCompatActivity {
    public Dialog progressdialog;

    public void myToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void progressDialog(Context context) {
        progressdialog = new Dialog(context);
        progressdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressdialog.getWindow().addFlags(2);
        progressdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressdialog.setContentView(R.layout.dialog_progress);
        ProgressBar progressBar;
        progressBar = progressdialog.findViewById(R.id.spin_view);
        FadingCircle fadingCircle = new FadingCircle();
        progressBar.setIndeterminateDrawable(fadingCircle);
        progressdialog.show();
    }
}
