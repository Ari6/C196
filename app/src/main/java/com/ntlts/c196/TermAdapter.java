package com.ntlts.c196;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermHolder> {
    private List<Term> termList;
    private OnClickListener onClickListener;

    public TermAdapter(List<Term> termList, OnClickListener onClickListener){
        this.termList = termList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.term_view, parent, false);
        TermHolder vh = new TermHolder(inflate, onClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TermHolder holder, int position) {
        holder.title.setText(termList.get(position).getTitle());
        holder.startDate.setText(termList.get(position).getStartDate());
        holder.endDate.setText(termList.get(position).getEndDate());
    }

    @Override
    public int getItemCount() {
        return termList.size();
    }

    /*
    Interface for OnClickListener
     */
    public interface OnClickListener {
        void onClick(int position);
    }

    public static class TermHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public TextView title;
        public TextView startDate;
        public TextView endDate;
        public OnClickListener onClickListener;

        public TermHolder(@NonNull View itemView, OnClickListener onClickListener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.termTitle);
            startDate = (TextView) itemView.findViewById(R.id.startDate);
            endDate = (TextView) itemView.findViewById(R.id.endDate);
            this.onClickListener = onClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(getAdapterPosition());
        }
    }
}
