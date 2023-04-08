package com.inficta.alokitsupport.Network;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import androidx.annotation.NonNull;
import okhttp3.logging.HttpLoggingInterceptor;

public class CustomHttp implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(@NonNull String message) {
        final String logName = "OkHttp";
        if (!message.startsWith("{")) {
            Log.d(logName, message);
            return;
        }
        try {
            String prettyPrintJson = new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(message));
            Log.d(logName, prettyPrintJson);
        } catch (JsonSyntaxException m) {
            Log.d(logName, message);
        }
    }
}
