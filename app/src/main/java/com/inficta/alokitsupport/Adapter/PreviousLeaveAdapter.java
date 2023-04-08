package com.inficta.alokitsupport.Adapter;

import static com.inficta.alokitsupport.Base.Util.loadImgUrl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;

import com.inficta.alokitsupport.Model.IssueModel;
import com.inficta.alokitsupport.Model.PreviousLeaveModel;
import com.inficta.alokitsupport.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreviousLeaveAdapter extends RecyclerView.Adapter<PreviousLeaveAdapter.ViewHolder> {
    Context context;
    List<PreviousLeaveModel.Datum> mainCatModels;


    public PreviousLeaveAdapter(Context context, List<PreviousLeaveModel.Datum> mainCatModels) {
        this.context = context;
        this.mainCatModels = mainCatModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_p_leave, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.date.setText(mainCatModels.get(position).getDate());
        holder.reason.setText(mainCatModels.get(position).getReason());
        holder.status.setText(mainCatModels.get(position).getStatus());
        if (mainCatModels.get(position).getImage().equals("")){
            holder.img.setImageResource(R.drawable.al_logo);
        }else {
            List<String> strings = new ArrayList<>(Arrays.asList(mainCatModels.get(position).getImage()));
            loadImgUrl(context, strings.get(0), holder.img);
        }
    }

    @Override
    public int getItemCount() {
        return mainCatModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView reason, status, date;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reason = itemView.findViewById(R.id.reason);
            status = itemView.findViewById(R.id.status);
            date = itemView.findViewById(R.id.date);
            img = itemView.findViewById(R.id.img);
        }
    }
}
