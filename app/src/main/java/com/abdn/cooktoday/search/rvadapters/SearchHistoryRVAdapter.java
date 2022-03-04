package com.abdn.cooktoday.search.rvadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abdn.cooktoday.R;

import java.util.List;

public class SearchHistoryRVAdapter extends RecyclerView.Adapter<SearchHistoryRVAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private SearchHistoryRVAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public SearchHistoryRVAdapter(Context context, List<String> data) {
        // todo: remove public constructor -- not good
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public SearchHistoryRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_search_history, parent, false);
        return new SearchHistoryRVAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(SearchHistoryRVAdapter.ViewHolder holder, int position) {
        String history = mData.get(position);
        holder.tvHistoryItem.setText(history);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvHistoryItem;

        ViewHolder(View itemView) {
            super(itemView);
            tvHistoryItem = itemView.findViewById(R.id.tvSearchHistory);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onSearchHistoryItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(SearchHistoryRVAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onSearchHistoryItemClick(View view, int position);
    }
}
