
package com.inficta.alokitsupport.Base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Util {

    public static void intent(Context context, Class aClass, String key, String value) {
        Intent intent = new Intent(context, aClass);
        if (key != null)
            intent.putExtra(key, value);
        else
            context.startActivity(intent);
    }

    public static void loadImgUrl(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).centerCrop().into(imageView);
        //override(200,200).
    }

    public static boolean isEmptyOrNull(String str) {
        if (str == null)
            return false;
        if (str.isEmpty())
            return false;
        if (str.equals("null"))
            return false;
        if (str.equals(""))
            return false;
        return true;
    }

    public static boolean isEmptyOrNullArray(String[] str) {
        boolean notNull = false;
        for (int i = 0; i < str.length; i++) {
            if (str[i] == null) {
                notNull = false;
                break;
            } else if (str[i].isEmpty()) {
                notNull = false;
                break;
            } else if (str[i].equals("")) {
                notNull = false;
                break;
            } else {
                notNull = true;
            }
        }
        return notNull;
    }

    public static void alertDialog(Context context, String title, String info) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //isOpen = false;
        builder.setTitle(title)
                .setMessage(info)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alert = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        alert.show();
    }
}

