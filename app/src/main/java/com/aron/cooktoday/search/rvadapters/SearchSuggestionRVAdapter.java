package com.aron.cooktoday.search.rvadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aron.cooktoday.R;

import java.util.List;

public class SearchSuggestionRVAdapter extends RecyclerView.Adapter<SearchSuggestionRVAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private SearchSuggestionRVAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public SearchSuggestionRVAdapter(Context context, List<String> data) {
        // todo: remove public constructor -- not good
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public SearchSuggestionRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_search_suggestions, parent, false);
        return new SearchSuggestionRVAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(SearchSuggestionRVAdapter.ViewHolder holder, int position) {
        String suggestion = mData.get(position);
        holder.myTv.setText(suggestion);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTv;

        ViewHolder(View itemView) {
            super(itemView);
            myTv = itemView.findViewById(R.id.tvSearchSuggestion);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onSearchSuggestionItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(SearchSuggestionRVAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onSearchSuggestionItemClick(View view, int position);
    }
}
