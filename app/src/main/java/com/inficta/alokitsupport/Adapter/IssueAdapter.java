package com.inficta.alokitsupport.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;


import com.inficta.alokitsupport.Model.IssueModel;
import com.inficta.alokitsupport.R;

import java.util.List;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.ViewHolder> {
    Context context;
    List<IssueModel.Datum> mainCatModels;
    private OnItemClick onItemClick;

    public interface OnItemClick {
        void onClick(IssueModel.Datum mainCatModel);
    }

    public IssueAdapter(Context context, List<IssueModel.Datum> mainCatModels, OnItemClick onItemClick) {
        this.context = context;
        this.mainCatModels = mainCatModels;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_issue, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(mainCatModels.get(position).getAssetsIssueId());
        holder.aid.setText(mainCatModels.get(position).getAsUnId());
        holder.aName.setText(mainCatModels.get(position).getAssetsName());
        holder.uName.setText(mainCatModels.get(position).getUserName());
        holder.date.setText(mainCatModels.get(position).getDate());


        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onClick(mainCatModels.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainCatModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, aid, aName, uName, date;
        ImageFilterView img_edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            aid = itemView.findViewById(R.id.aid);
            aName = itemView.findViewById(R.id.aName);
            uName = itemView.findViewById(R.id.uName);
            date = itemView.findViewById(R.id.date);
            img_edit = itemView.findViewById(R.id.img_edit);
        }
    }
}
