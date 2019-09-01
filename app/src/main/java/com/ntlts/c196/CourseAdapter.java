package com.ntlts.c196;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    private List<Course> courseList;
    private OnCourseClickListener mOnCourseClickListener;

    public CourseAdapter(List<Course> courseList, OnCourseClickListener onCourseClickListener){
        this.courseList = courseList;
        this.mOnCourseClickListener = onCourseClickListener;
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.course_view, parent, false);
        CourseHolder vh = new CourseHolder(inflate, mOnCourseClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        holder.courseName.setText(courseList.get(position).getTitle());
        holder.status.setText(courseList.get(position).getStatus());
        holder.start.setText(courseList.get(position).getStart());
        holder.anticipatedEnd.setText(courseList.get(position).getAnticipatedEnd());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    /*
    CourseHolder class
     */

    public static class CourseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView courseName;
        TextView status;
        TextView start;
        TextView anticipatedEnd;
        OnCourseClickListener onCourseClickListener;

        public CourseHolder(@NonNull View itemView, OnCourseClickListener onCourseClickListener) {
            super(itemView);
            courseName = itemView.findViewById(R.id.progressCourseName);
            status = itemView.findViewById(R.id.status);
            start = itemView.findViewById(R.id.courseStart);
            anticipatedEnd = itemView.findViewById(R.id.anticipatedEnd);
            this.onCourseClickListener = onCourseClickListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            onCourseClickListener.onClick(getAdapterPosition());
        }
    }
    /*
      An interface for Listener
     */
    public interface OnCourseClickListener {
        public void onClick(int position);
    }
}
