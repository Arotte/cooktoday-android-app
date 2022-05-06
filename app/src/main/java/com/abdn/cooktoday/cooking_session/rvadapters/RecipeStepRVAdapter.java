package com.abdn.cooktoday.cooking_session.rvadapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abdn.cooktoday.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepRVAdapter extends RecyclerView.Adapter<RecipeStepRVAdapter.ViewHolder> {

    private enum State {
        NOT_STARTED,
        ONGOING,
        FINISHED,
        PAUSED
    }

    private final List<State> states;
    private final List<String> steps;
    private final List<String> stepTypes;
    private final int nSteps;

    // a very bad solution to an equally awful problem
    private List<MaterialCardView> cardViews;
    private List<ImageView> checkImages;
    private List<ProgressBar> progressBars;

    private final LayoutInflater mInflater;
    private RecipeStepRVAdapter.ItemClickListener mClickListener;

    public RecipeStepRVAdapter(Context context, List<String> steps, List<String> stepTypes) {
        this.mInflater = LayoutInflater.from(context);
        this.steps = steps;
        this.stepTypes = stepTypes;
        this.nSteps = this.steps.size();

        this.states = new ArrayList<>();
        for (int i = 0; i < nSteps; i++)
            this.states.add(State.NOT_STARTED);

        cardViews = new ArrayList<>();
        checkImages = new ArrayList<>();
        progressBars = new ArrayList<>();
    }

    @Override
    public RecipeStepRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cooking_sess_rv_row_recipe_step_v1, parent, false);
        return new RecipeStepRVAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @SuppressLint("ResourceAsColor") //TODO: FIND A FIX
    @Override
    public void onBindViewHolder(RecipeStepRVAdapter.ViewHolder holder, int position) {
        //String stepText = steps.get(position);
        String currentStep = "Step " + (position + 1);
        String totalSteps = "/ " + this.nSteps;

        // set step card to inactive
        // holder.tvStepText.setText(Html.fromHtml(stepText));
        holder.tvStepCounter.setText(currentStep);
        holder.tvTotalSteps.setText(totalSteps);
        // holder.tvStepType.setText(stepTypes.get(position));

        holder.stepCard.setStrokeWidth(1);
        holder.stepCard.setStrokeColor(Color.parseColor("#9FA5C0")); // textSecondary
        holder.stepCard.setCardBackgroundColor(Color.parseColor("#F4F5F7")); // form
        holder.progressBar.setProgress(0);
        holder.check.setImageResource(R.drawable.ic_check_inactive);
        holder.check.setVisibility(View.INVISIBLE);
        // holder.stepTypeCard.setCardBackgroundColor(Color.WHITE);
        // holder.tvStepType.setTextColor(Color.parseColor("#9FA5C0")); // textSecondary
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // TextView tvStepText;
        TextView tvStepCounter;
        TextView tvTotalSteps;
        // TextView tvStepType;

        MaterialCardView stepCard;
        ProgressBar progressBar;
        ImageView check;
        // MaterialCardView stepTypeCard;


        ViewHolder(View itemView) {
            super(itemView);
            // tvStepText     = itemView.findViewById(R.id.tvRVRecipeStepText);
            tvStepCounter  = itemView.findViewById(R.id.tvCookingSessStepCount);
            tvTotalSteps   = itemView.findViewById(R.id.tvCookingSessMaxSteps);
            // tvStepType     = itemView.findViewById(R.id.tvCookingSessStepType);
            stepCard       = itemView.findViewById(R.id.containerCookingSessStepCard);
            progressBar    = itemView.findViewById(R.id.pbCookingSessStepPB);
            check          = itemView.findViewById(R.id.ivCookingSessStepCheck);
            // stepTypeCard   = itemView.findViewById(R.id.cardCookingSessStepType);
            itemView.setOnClickListener(this);

            cardViews.add(stepCard);
            progressBars.add(progressBar);
            checkImages.add(check);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onStepItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return steps.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(RecipeStepRVAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onStepItemClick(View view, int position);
    }


    public void markItemAsDone(int id) {
        if (id > -1) {
            ImageView check = checkImages.get(id);
            check.setVisibility(View.VISIBLE);
            check.setImageResource(R.drawable.ic_check_2);
            progressBars.get(id).setProgress(100);
            states.set(id, State.FINISHED);
        }
    }

    public void activateItem(int id) {
        MaterialCardView stepCard = cardViews.get(id);
        stepCard.setStrokeWidth(3);
        stepCard.setStrokeColor(Color.parseColor("#1FCC79"));
    }

    public void deactivateItem(int id) {
        MaterialCardView stepCard = cardViews.get(id);
        if (states.get(id) == State.FINISHED) {
            stepCard.setStrokeWidth(0);
        } else {
            stepCard.setStrokeWidth(1);
            stepCard.setStrokeColor(Color.parseColor("#9FA5C0"));
        }
    }

}
