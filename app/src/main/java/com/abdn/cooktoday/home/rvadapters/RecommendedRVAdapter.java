package com.abdn.cooktoday.home.rvadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecommendedRVAdapter extends RecyclerView.Adapter<RecommendedRVAdapter.ViewHolder> {

    private List<Recipe> recipes;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public RecommendedRVAdapter(Context context, List<Recipe> data) {
        this.mInflater = LayoutInflater.from(context);
        this.recipes = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_recipe_card_v2, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name     = recipes.get(position).getName();
        String imgUrl   = recipes.get(position).getImgUrl();
        String calories = recipes.get(position).getCalories() + " kcal";
        String time     = recipes.get(position).getTimePretty(Recipe.TimeType.FULL_COOKING_TIME);
        String servings = recipes.get(position).getServings() + " servings";

        // set recipe name
        holder.tvRecipeName.setText(name);
        // set calories & servings & time textviews
        holder.tvKcal.setText(calories);
        holder.tvTime.setText(time);
        holder.tvServings.setText(servings);
        // download and show recipe img
        Picasso.get().load(imgUrl).into(holder.ivRecipeImg);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return recipes.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView    tvRecipeName;
        TextView    tvKcal;
        TextView    tvTime;
        TextView    tvServings;
        ImageView   ivRecipeImg;

        ViewHolder(View itemView) {
            super(itemView);
            tvRecipeName   = itemView.findViewById(R.id.tvRecipeCardV2RecipeName);
            tvKcal         = itemView.findViewById(R.id.tvRecipeCardV2Calories);
            tvServings     = itemView.findViewById(R.id.tvRecipeCardV2Servings);
            tvTime         = itemView.findViewById(R.id.tvRecipeCardV2CookingTime);
            ivRecipeImg    = itemView.findViewById(R.id.ivRecipeCardV2RecipeImg);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onRecItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Recipe getItem(int id) {
        return recipes.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onRecItemClick(View view, int position);
    }
}