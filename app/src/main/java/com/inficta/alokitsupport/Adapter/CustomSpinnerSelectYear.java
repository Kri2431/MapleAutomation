package com.inficta.alokitsupport.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import com.inficta.alokitsupport.Model.StstusModel;
import com.inficta.alokitsupport.Model.YearModel;
import com.inficta.alokitsupport.R;

import java.util.List;

public class CustomSpinnerSelectYear extends BaseAdapter {
    Context context;
    List<YearModel.Datum> stateModels;
    LayoutInflater inflater;

    public CustomSpinnerSelectYear(Context context, List<YearModel.Datum> stateModels) {
        this.context = context;
        this.stateModels = stateModels;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return stateModels.size();
    }

    @Override
    public Object getItem(int i) {
        return stateModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.customer_spinner_adapter, null);
        AppCompatTextView txtCreatedBy = view.findViewById(R.id.txt_created_by);
        txtCreatedBy.setText(stateModels.get(i).getYear());
        return view;
    }
}
