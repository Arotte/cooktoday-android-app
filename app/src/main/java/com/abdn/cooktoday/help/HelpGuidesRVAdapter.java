package com.abdn.cooktoday.help;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abdn.cooktoday.R;

import java.util.List;

public class HelpGuidesRVAdapter extends RecyclerView.Adapter<HelpGuidesRVAdapter.ViewHolder> {

    private final List<HelpTocItem> mData;
    private final LayoutInflater mInflater;
    private HelpGuidesRVAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public HelpGuidesRVAdapter(Context context, List<HelpTocItem> data) {
        // todo: remove public constructor -- not good
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public HelpGuidesRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_help_guide_item, parent, false);
        return new HelpGuidesRVAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(HelpGuidesRVAdapter.ViewHolder holder, int position) {
        String guideName = mData.get(position).getTitle();
        holder.tvGuideItem.setText(guideName);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvGuideItem;

        ViewHolder(View itemView) {
            super(itemView);
            tvGuideItem = itemView.findViewById(R.id.tvGuideItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onHelpGuideItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public HelpTocItem getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(HelpGuidesRVAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onHelpGuideItemClick(View view, int position);
    }
}
