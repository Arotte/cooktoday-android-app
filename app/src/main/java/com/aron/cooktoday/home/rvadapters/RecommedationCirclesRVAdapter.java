package com.aron.cooktoday.home.rvadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aron.cooktoday.R;

import java.util.List;

public class RecommedationCirclesRVAdapter extends RecyclerView.Adapter<RecommedationCirclesRVAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private RecommedationCirclesRVAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public RecommedationCirclesRVAdapter(Context context, List<String> data) {
        // todo: remove public constructor -- not good
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public RecommedationCirclesRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_home_story_suggestions, parent, false);
        return new RecommedationCirclesRVAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(RecommedationCirclesRVAdapter.ViewHolder holder, int position) {
        String name = mData.get(position);
        holder.myTextView.setText(name);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvHomeFeedCategorySuggestion);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onCircleItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(RecommedationCirclesRVAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onCircleItemClick(View view, int position);
    }
}
