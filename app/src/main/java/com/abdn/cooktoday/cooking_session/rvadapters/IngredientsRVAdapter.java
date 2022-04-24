package com.abdn.cooktoday.cooking_session.rvadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.help.HelpTocItem;
import com.abdn.cooktoday.local_data.model.Ingredient;

import org.w3c.dom.Text;

import java.util.List;

public class IngredientsRVAdapter extends RecyclerView.Adapter<IngredientsRVAdapter.ViewHolder> {

    private List<Ingredient> mData;
    private LayoutInflater mInflater;
    private IngredientsRVAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public IngredientsRVAdapter(Context context, List<Ingredient> data) {
        // todo: remove public constructor -- not good
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public IngredientsRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_cooking_sess_ingredient_item, parent, false);
        return new IngredientsRVAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(IngredientsRVAdapter.ViewHolder holder, int position) {
        String name = mData.get(position).getString();
        holder.tvName.setText(name);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCookingSessIngredName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onIngredientItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Ingredient getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(IngredientsRVAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onIngredientItemClick(View view, int position);
    }
}
