package com.abdn.cooktoday.help;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abdn.cooktoday.R;

import java.util.ArrayList;
import java.util.List;


public class HelpActivity extends AppCompatActivity
    implements HelpGuidesRVAdapter.ItemClickListener {

    // static fields
    private static final String TAG = "HelpActivity";
    private static final List<HelpTocItem> tocItems = new ArrayList<>();
    static {
        tocItems.add(new HelpTocItem("How to Search Recipes", "Recipe Search", R.layout.content_help_how_to_search));
        tocItems.add(new HelpTocItem("How to Add a Recipe", "Add Recipe", R.layout.content_help_how_to_add_recipe));
        tocItems.add(new HelpTocItem("How to Add a Recipe from a URL", "Add Recipe URL", -1));
        tocItems.add(new HelpTocItem("How to Start Cooking a Recipe", "Cook Recipe", -1));
        tocItems.add(new HelpTocItem("How to Save a Recipe", "Save Recipe", -1));
        tocItems.add(new HelpTocItem("How to Log Out", "Log Out", -1));
    }
    private static final int tocLayout = R.layout.content_help_toc;
    private static final String defaultTitle = "Help Centre";

    // ui elements
    private LinearLayout container;
    private Button cancelButton;
    private ImageView arrowBack;
    private TextView navTitle;

    // ui helper elements
    private HelpGuidesRVAdapter tocAdapter;

    // vars
    private int currentlyDisplayedTocItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        arrowBack = findViewById(R.id.helpArrowBack);
        arrowBack.setVisibility(View.GONE);
        handleBackButton();
        navTitle = findViewById(R.id.helpTitle);

        container = findViewById(R.id.llHelpContainer);
        tocAdapter = new HelpGuidesRVAdapter(this, tocItems);
        tocAdapter.setClickListener(this);

        // display the toc
        currentlyDisplayedTocItem = -1;
        display(tocLayout, -1);

        // inflate cancel button
        cancelButton = findViewById(R.id.btnCancelHelp);
        handleCancelButton();
    }

    // ===============================================================
    // Click Listeners
    // ===============================================================

    @Override
    public void onHelpGuideItemClick(View view, int position) {
        Log.i(TAG, "Displaying help guide item: " + position + " (" + tocItems.get(position).getTitle() + ")");
        display(tocItems.get(position).getLayoutId(), position);
    }

    private void handleBackButton() {
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display(tocLayout, -1);
            }
        });
    }

    private void handleCancelButton() {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // ===============================================================
    // Helper Methods
    // ===============================================================

    private void display(int layout, int position) {
        if (layout >= 0) {
            View view = LayoutInflater.from(this).inflate(layout, null);
            if (layout == tocLayout) {
                RecyclerView rvToc = view.findViewById(R.id.rvHelpToc);
                rvToc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                rvToc.setAdapter(tocAdapter);
                setTitle(defaultTitle);
                hideBackButton();
            } else {
                setTitle(tocItems.get(position).getShortTitle());
                showBackButton();
            }
            container.removeAllViews();
            container.addView(view);
            currentlyDisplayedTocItem = layout;
        }
    }

    private void hideBackButton() {
        arrowBack.setVisibility(View.GONE);
    }

    private void showBackButton() {
        arrowBack.setVisibility(View.VISIBLE);
    }

    private void setTitle(String title) {
        navTitle.setText(title);
    }
}