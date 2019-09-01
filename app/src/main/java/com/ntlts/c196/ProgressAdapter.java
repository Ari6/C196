package com.ntlts.c196;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressHolder> {
    private List<Progress> progressList;
    private OnClickListener onClickListener;

    public ProgressAdapter(List<Progress> progressList, OnClickListener onClickListener) {
        this.progressList = progressList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ProgressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.progress_view, parent, false);
        ProgressHolder vh = new ProgressHolder(inflate, onClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressHolder holder, int position) {
        holder.termName.setText(progressList.get(position).getTermName());
        holder.courseName.setText(progressList.get(position).getCourseName());
        holder.anticipatedEndDate.setText(progressList.get(position).getAnticipatedEndDate());
        holder.assessmentProgress.setText(progressList.get(position).getAssessmentProgress());
    }

    @Override
    public int getItemCount() {
        return progressList.size();
    }

    /*
    Interface for OnClickListener
     */
    public interface OnClickListener {
        void onClick(int position);
    }

    public static class ProgressHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public TextView termName;
        public TextView courseName;
        public TextView anticipatedEndDate;
        public TextView assessmentProgress;
        public OnClickListener onClickListener;

        public ProgressHolder(@NonNull View itemView, OnClickListener onClickListener) {
            super(itemView);
            termName = (TextView) itemView.findViewById(R.id.progressTermName);
            courseName = (TextView) itemView.findViewById(R.id.progressCourseName);
            anticipatedEndDate = (TextView) itemView.findViewById(R.id.progressAnticipatedEnd);
            assessmentProgress = (TextView) itemView.findViewById(R.id.progressAssessment);

            this.onClickListener = onClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(getAdapterPosition());
        }
    }
}
