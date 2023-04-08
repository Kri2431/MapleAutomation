package com.inficta.alokitsupport.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;

import com.inficta.alokitsupport.Model.ExpiredDateTimeModel;
import com.inficta.alokitsupport.Model.IssueModel;
import com.inficta.alokitsupport.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

public class ExpireTimeAdapter extends RecyclerView.Adapter<ExpireTimeAdapter.ViewHolder> {
    Context context;
    List<ExpiredDateTimeModel.Datum> mainCatModels;
    private OnItemClick onItemClick;

    public interface OnItemClick {
        void onClick(IssueModel.Datum mainCatModel);
    }

    public ExpireTimeAdapter(Context context, List<ExpiredDateTimeModel.Datum> mainCatModels, OnItemClick onItemClick) {
        this.context = context;
        this.mainCatModels = mainCatModels;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.call_expired, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.srNo.setText(mainCatModels.get(position).getSrNo());
        holder.uName.setText(mainCatModels.get(position).getUserName());
        holder.date.setText(mainCatModels.get(position).getDate());
        holder.uIId.setText(mainCatModels.get(position).getIssueId());
        holder.aNo.setText(mainCatModels.get(position).getAssetsNo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
        String countDate = mainCatModels.get(position).getCurrtentDateTime();
        String countDownDate = mainCatModels.get(position).getEndTime();
        Date now  = new Date();
        try{
            Date date = sdf.parse(countDate);
            Date date1 = sdf.parse(countDownDate);
            long cuurentDate = date1.getTime();
            long newYearDate = date.getTime();
            long countDownToNewYear = cuurentDate- newYearDate;
            holder.countDownView.start(countDownToNewYear);
        }catch (ParseException e){
            e.printStackTrace();
        }

        if (holder.countDownView.equals("00:00:00:00")){
            holder.tUp.setVisibility(View.VISIBLE);
        }else {
            holder.tIn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mainCatModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView srNo, uName, date, uIId, aNo, tIn, tUp;

        CountdownView countDownView;
        ImageFilterView img_edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            srNo = itemView.findViewById(R.id.srNo);
            uName = itemView.findViewById(R.id.uName);
            date = itemView.findViewById(R.id.date);
            uIId = itemView.findViewById(R.id.uIId);
            aNo = itemView.findViewById(R.id.aNo);
            tIn = itemView.findViewById(R.id.tIn);
            tUp = itemView.findViewById(R.id.tUp);
            countDownView = itemView.findViewById(R.id.countDownView);
        }
    }
}
