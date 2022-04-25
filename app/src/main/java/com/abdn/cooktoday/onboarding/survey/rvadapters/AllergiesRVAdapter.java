package com.abdn.cooktoday.onboarding.survey.rvadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abdn.cooktoday.R;

import java.util.List;

public class AllergiesRVAdapter extends RecyclerView.Adapter<AllergiesRVAdapter.ViewHolder> {

    private final List<String> allergyNames;
    private final LayoutInflater mInflater;
    private AllergiesRVAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public AllergiesRVAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.allergyNames = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public AllergiesRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclervew_row_survey_selectable_circle, parent, false);
        return new AllergiesRVAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(AllergiesRVAdapter.ViewHolder holder, int position) {
        String cuisineName = allergyNames.get(position);
        holder.tvCuisineName.setText(cuisineName);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return allergyNames.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvCuisineName;

        ViewHolder(View itemView) {
            super(itemView);
            tvCuisineName = itemView.findViewById(R.id.tvSurveySelectableCircleName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onAllergyItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return allergyNames.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(AllergiesRVAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onAllergyItemClick(View view, int position);
    }
}