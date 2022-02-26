package com.aron.cooktoday.home.rvadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aron.cooktoday.R;
import com.aron.cooktoday.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HotRecipesRVAdapter extends RecyclerView.Adapter<HotRecipesRVAdapter.ViewHolder> {

    private final List<Recipe> hotRecipes;
    private final LayoutInflater mInflater;
    private HotRecipesRVAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public HotRecipesRVAdapter(Context context, List<Recipe> data) {
        this.mInflater = LayoutInflater.from(context);
        this.hotRecipes = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public HotRecipesRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_recipe_card_v2_small, parent, false);
        return new HotRecipesRVAdapter.ViewHolder(view);
    }

    // binds the data to the views in each row
    @Override
    public void onBindViewHolder(HotRecipesRVAdapter.ViewHolder holder, int position) {
        String name     = hotRecipes.get(position).getName();
        String imgURL   = hotRecipes.get(position).getImgUrl();
        String calories = hotRecipes.get(position).getCalories() + " kcal";
        String time     = hotRecipes.get(position).getFullPrepTimePretty();

        // set recipe name
        holder.tvRecipeName.setText(name);
        // set kcal & time
        holder.tvKcal.setText(calories);
        holder.tvTime.setText(time);
        // download and show recipe img
        Picasso.get().load(imgURL).into(holder.ivRecipeImg);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return hotRecipes.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView   tvRecipeName;
        ImageView  ivRecipeImg;
        TextView   tvKcal;
        TextView   tvTime;

        ViewHolder(View itemView) {
            super(itemView);
            tvRecipeName = itemView.findViewById(R.id.tvRecipeCardV2SmallRecipeName);
            tvKcal       = itemView.findViewById(R.id.tvRecipeCardV2SmallRecipeKcal);
            tvTime       = itemView.findViewById(R.id.tvRecipeCardV2SmallFullCookTime);
            ivRecipeImg  = itemView.findViewById(R.id.ivRecipeCardV2SmallRecipeImg);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onHotItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Recipe getItem(int id) {
        return hotRecipes.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(HotRecipesRVAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onHotItemClick(View view, int position);
    }
}