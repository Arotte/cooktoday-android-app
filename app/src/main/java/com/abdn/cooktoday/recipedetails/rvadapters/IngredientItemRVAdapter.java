package com.abdn.cooktoday.recipedetails.rvadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.local_data.model.Ingredient;

import java.util.List;

public class IngredientItemRVAdapter extends RecyclerView.Adapter<IngredientItemRVAdapter.ViewHolder> {

    private final List<Ingredient> mData;
    private final LayoutInflater mInflater;
    private IngredientItemRVAdapter.ItemClickListener mClickListener;

    public IngredientItemRVAdapter(Context context, List<Ingredient> data) {
        // todo: remove public constructor -- not good
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public IngredientItemRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_ingredient_item, parent, false);
        return new IngredientItemRVAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(IngredientItemRVAdapter.ViewHolder holder, int position) {
        String ingredName = mData.get(position).getString();
        holder.tvIngredientName.setText(ingredName);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvIngredientName;

        ViewHolder(View itemView) {
            super(itemView);
            tvIngredientName = itemView.findViewById(R.id.tvIngredientItemName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onIngredItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Ingredient getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(IngredientItemRVAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onIngredItemClick(View view, int position);
    }
}
