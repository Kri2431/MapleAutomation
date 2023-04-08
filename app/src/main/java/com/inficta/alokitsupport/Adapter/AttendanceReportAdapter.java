package com.inficta.alokitsupport.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;

import com.inficta.alokitsupport.Model.AttendanceReportModel;
import com.inficta.alokitsupport.Model.IssueModel;
import com.inficta.alokitsupport.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

public class AttendanceReportAdapter extends RecyclerView.Adapter<AttendanceReportAdapter.ViewHolder> {
    Context context;
    List<AttendanceReportModel.Datum> mainCatModels;

    public AttendanceReportAdapter(Context context, List<AttendanceReportModel.Datum> mainCatModels) {
        this.context = context;
        this.mainCatModels = mainCatModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.attendance_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.inTime.setText(mainCatModels.get(position).getInTime());
        holder.inDate.setText(mainCatModels.get(position).getInDate());
        holder.inPlant.setText(mainCatModels.get(position).getInPlantName());
        holder.outTime.setText(mainCatModels.get(position).getOutTime());
        holder.outDate.setText(mainCatModels.get(position).getOutDate());
        holder.outPlant.setText(mainCatModels.get(position).getOutPlantName());
    }

    @Override
    public int getItemCount() {
        return mainCatModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView inTime,inDate,inPlant,outTime,outDate,outPlant;

        CountdownView countDownView;
        ImageFilterView img_edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            inTime = itemView.findViewById(R.id.inTime);
            inDate = itemView.findViewById(R.id.inDate);
            inPlant = itemView.findViewById(R.id.inPlant);
            outTime = itemView.findViewById(R.id.outTime);
            outDate = itemView.findViewById(R.id.outDate);
            outPlant = itemView.findViewById(R.id.outPlant);
        }
    }
}
