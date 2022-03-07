package com.abdn.cooktoday.onboarding.survey.rvadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abdn.cooktoday.R;

import java.util.List;

public class DietsRVAdapter extends RecyclerView.Adapter<DietsRVAdapter.ViewHolder> {

    private List<String> dietNames;
    private LayoutInflater mInflater;
    private DietsRVAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public DietsRVAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.dietNames = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public DietsRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclervew_row_survey_selectable_circle, parent, false);
        return new DietsRVAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(DietsRVAdapter.ViewHolder holder, int position) {
        String dietName = dietNames.get(position);
        holder.tvDietName.setText(dietName);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return dietNames.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvDietName;

        ViewHolder(View itemView) {
            super(itemView);
            tvDietName = itemView.findViewById(R.id.tvSurveySelectableCircleName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onDietItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return dietNames.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(DietsRVAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onDietItemClick(View view, int position);
    }
}