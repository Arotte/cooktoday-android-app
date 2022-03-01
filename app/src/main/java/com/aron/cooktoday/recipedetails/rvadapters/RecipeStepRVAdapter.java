package com.aron.cooktoday.recipedetails.rvadapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aron.cooktoday.R;

import java.util.List;

public class RecipeStepRVAdapter extends RecyclerView.Adapter<RecipeStepRVAdapter.ViewHolder> {

    private List<String> steps;
    private int nSteps;
    private static final String tvStepTextPrepend = "Step ";
    private static final String tvTotalStepsPrepend = "/ ";

    private LayoutInflater mInflater;
    private RecipeStepRVAdapter.ItemClickListener mClickListener;

    public RecipeStepRVAdapter(Context context, List<String> data) {
        // todo: remove public constructor -- not good
        this.mInflater = LayoutInflater.from(context);
        this.steps = data;
        this.nSteps = this.steps.size();
    }

    @Override
    public RecipeStepRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_recipe_directions_step, parent, false);
        return new RecipeStepRVAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(RecipeStepRVAdapter.ViewHolder holder, int position) {
        String stepText = steps.get(position);
        String currentStep = tvStepTextPrepend + (position + 1);
        String totalSteps = tvTotalStepsPrepend + this.nSteps;

        holder.tvStepText.setText(Html.fromHtml(stepText));
        holder.tvStepCounter.setText(currentStep);
        holder.tvTotalSteps.setText(totalSteps);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return steps.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvStepText;
        TextView tvStepCounter;
        TextView tvTotalSteps;

        ViewHolder(View itemView) {
            super(itemView);
            tvStepText     = itemView.findViewById(R.id.tvRVRecipeStepText);
            tvStepCounter  = itemView.findViewById(R.id.tvRVRecipeStepCounter);
            tvTotalSteps   = itemView.findViewById(R.id.tvRVRecipeTotalSteps);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onStepItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return steps.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(RecipeStepRVAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onStepItemClick(View view, int position);
    }
}
