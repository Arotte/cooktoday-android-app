package com.abdn.cooktoday.upload_recipe.manual.rvadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.local_data.model.Ingredient;
import com.abdn.cooktoday.local_data.model.NerredIngred;

import java.util.List;

public class IngredientRVAdapter extends RecyclerView.Adapter<IngredientRVAdapter.ViewHolder> {

    private List<Ingredient> mData;
    private int quantityColor;
    private int unitColor;
    private int nameColor;
    private LayoutInflater mInflater;
    private IngredientRVAdapter.ItemClickListener mClickListener;

    public IngredientRVAdapter(Context context, List<Ingredient> data, int quantityColor, int unitColor, int nameColor) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.quantityColor = quantityColor;
        this.unitColor = unitColor;
        this.nameColor = nameColor;
    }

    @Override
    public IngredientRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_ingredient_item_add_recipe, parent, false);
        return new IngredientRVAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(IngredientRVAdapter.ViewHolder holder, int position) {
        holder.tvIngredientName.setText(mData.get(position).getName());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        TextView tvIngredientName;
        // TextView tvIngredientQuantity;

        ViewHolder(View itemView) {
            super(itemView);
            tvIngredientName = itemView.findViewById(R.id.tvIngredientItemName);
            // tvIngredientQuantity = itemView.findViewById(R.id.tvIngredientQuantity);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onIngredItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            if (mClickListener != null) mClickListener.onIngredItemLongClick(view, getAdapterPosition());
            return true;
        }
    }

    // convenience method for getting data at click position
    public Ingredient getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(IngredientRVAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onIngredItemClick(View view, int position);
        void onIngredItemLongClick(View view, int position);
    }
}
