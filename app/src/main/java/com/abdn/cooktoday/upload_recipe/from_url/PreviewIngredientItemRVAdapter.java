package com.abdn.cooktoday.upload_recipe.from_url;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.local_data.model.Ingredient;

import java.util.List;

public class PreviewIngredientItemRVAdapter extends RecyclerView.Adapter<PreviewIngredientItemRVAdapter.ViewHolder> {

    private List<Ingredient> mData;
    private LayoutInflater mInflater;
    private PreviewIngredientItemRVAdapter.ItemClickListener mClickListener;

    public PreviewIngredientItemRVAdapter(Context context, List<Ingredient> data) {
        // todo: remove public constructor -- not good
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public PreviewIngredientItemRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_preview_ingredient_item, parent, false);
        return new PreviewIngredientItemRVAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(PreviewIngredientItemRVAdapter.ViewHolder holder, int position) {
        String ingredName = mData.get(position).getName();
        // String ingredQuantity = mData.get(position).getQuantity();

        holder.tvIngredientName.setText(ingredName);
        // holder.tvIngredientQuantity.setText(ingredQuantity);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvIngredientName;
        // TextView tvIngredientQuantity;

        ViewHolder(View itemView) {
            super(itemView);
            tvIngredientName = itemView.findViewById(R.id.tvIngredientItemName);
            // tvIngredientQuantity = itemView.findViewById(R.id.tvIngredientQuantity);

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
    public void setClickListener(PreviewIngredientItemRVAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onIngredItemClick(View view, int position);
    }
}
