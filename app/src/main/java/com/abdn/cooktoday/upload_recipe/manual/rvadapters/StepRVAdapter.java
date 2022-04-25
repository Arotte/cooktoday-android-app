package com.abdn.cooktoday.upload_recipe.manual.rvadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abdn.cooktoday.R;

import java.util.List;

public class StepRVAdapter extends RecyclerView.Adapter<StepRVAdapter.ViewHolder> {

    private final List<String> mData;
    private final LayoutInflater mInflater;
    private StepRVAdapter.ItemClickListener mClickListener;

    public StepRVAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public StepRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_step_item_add_recipe, parent, false);
        return new StepRVAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(StepRVAdapter.ViewHolder holder, int position) {
        String step = mData.get(position);
        holder.tvStep.setText(step);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        TextView tvStep;

        ViewHolder(View itemView) {
            super(itemView);
            tvStep = itemView.findViewById(R.id.tvCreateRecipeAddedStep);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onStepItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            if (mClickListener != null) mClickListener.onStepItemLongClick(view, getAdapterPosition());
            return true;
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(StepRVAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onStepItemClick(View view, int position);
        void onStepItemLongClick(View view, int position);
    }
}
