package com.aron.cooktoday.onboarding.survey.rvadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aron.cooktoday.R;

import java.util.List;

public class IngredientsRVAdapter extends RecyclerView.Adapter<IngredientsRVAdapter.ViewHolder> {

    private List<String> ingredNames;
    private LayoutInflater mInflater;
    private IngredientsRVAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public IngredientsRVAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.ingredNames = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public IngredientsRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclervew_row_survey_selectable_circle, parent, false);
        return new IngredientsRVAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(IngredientsRVAdapter.ViewHolder holder, int position) {
        String ingredName = ingredNames.get(position);
        holder.tvIngredName.setText(ingredName);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return ingredNames.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvIngredName;

        ViewHolder(View itemView) {
            super(itemView);
            tvIngredName = itemView.findViewById(R.id.tvSurveySelectableCircleName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onIngredItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return ingredNames.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(IngredientsRVAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onIngredItemClick(View view, int position);
    }
}