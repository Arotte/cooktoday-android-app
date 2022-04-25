package com.abdn.cooktoday.onboarding.survey.rvadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abdn.cooktoday.R;

import java.util.List;

public class CuisinesRVAdapter extends RecyclerView.Adapter<CuisinesRVAdapter.ViewHolder> {

    private final List<String> mData;
    private final LayoutInflater mInflater;
    private CuisinesRVAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public CuisinesRVAdapter(Context context, List<String> data) {
        // todo: remove public constructor -- not good
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public CuisinesRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclervew_row_survey_selectable_circle, parent, false);
        return new CuisinesRVAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(CuisinesRVAdapter.ViewHolder holder, int position) {
        String cuisineName = mData.get(position);
        holder.tvCuisineName.setText(cuisineName);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
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
            if (mClickListener != null) mClickListener.onCuisineItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(CuisinesRVAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onCuisineItemClick(View view, int position);
    }
}