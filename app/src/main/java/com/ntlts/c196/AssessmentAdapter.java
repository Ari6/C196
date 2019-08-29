package com.ntlts.c196;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentHolder> {
    private List<Assessment> assessmentList;
    private OnAssessmentClickListener mOnAssessmentClickListener;

    public AssessmentAdapter(List<Assessment> assessmentList, OnAssessmentClickListener onAssessmentClickListener){
        this.assessmentList = assessmentList;
        this.mOnAssessmentClickListener = onAssessmentClickListener;
    }

    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.assessment_view, parent, false);
        AssessmentHolder vh = new AssessmentHolder(inflate, mOnAssessmentClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentHolder holder, int position) {
        holder.assessmentTitle.setText(assessmentList.get(position).getTitle());
        holder.assessmentOapa.setText(assessmentList.get(position).getOaPa());
        //holder.assessmentDueDate.setText(assessmentList.get(position).getDueDate());
        holder.assessmentDueDate.setText(assessmentList.get(position).getGoalDate());
        holder.assessmentPerformance.setText(assessmentList.get(position).getPerformance());
    }

    @Override
    public int getItemCount() {
        return assessmentList.size();
    }

    /*
    AssessmentHolder class
     */

    public static class AssessmentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView assessmentTitle;
        TextView assessmentOapa;
        TextView assessmentDueDate;
        TextView assessmentPerformance;
        OnAssessmentClickListener onAssessmentClickListener;

        public AssessmentHolder(@NonNull View itemView, OnAssessmentClickListener onAssessmentClickListener) {
            super(itemView);
            assessmentTitle = itemView.findViewById(R.id.assessmentTitle);
            assessmentOapa = itemView.findViewById(R.id.assessmentOaPa);
            assessmentPerformance = itemView.findViewById(R.id.assessmentPerformance);
            assessmentDueDate = itemView.findViewById(R.id.assessmentDueDate);
            this.onAssessmentClickListener = onAssessmentClickListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            onAssessmentClickListener.onClick(getAdapterPosition());
        }
    }
    /*
      An interface for Listener
     */
    public interface OnAssessmentClickListener {
        public void onClick(int position);
    }
}

