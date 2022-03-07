package com.abdn.cooktoday.search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.search.bottomsheet.SearchFilterBottomSheet;
import com.abdn.cooktoday.search.rvadapters.SearchHistoryRVAdapter;
import com.abdn.cooktoday.search.rvadapters.SearchSuggestionRVAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment
        implements
            SearchSuggestionRVAdapter.ItemClickListener,
            SearchHistoryRVAdapter.ItemClickListener
{

    EditText search;
    ImageView filter;

    SearchSuggestionRVAdapter searchSuggestionRVAdapter;
    SearchHistoryRVAdapter searchHistoryRVAdapter;
    List<String> searchHistory;
    List<String> searchSuggestions;

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_search, container, false);
        preventFlickering();

        // init elements
        search = (EditText) layout.findViewById(R.id.searchBar);
        filter = layout.findViewById(R.id.searchFilterIcon);
        RecyclerView rvSearchSuggestions = layout.findViewById(R.id.rvSearchSuggestions);
        RecyclerView rvSearchHistory = layout.findViewById(R.id.rvSearchHistory);

        searchSuggestions = new ArrayList<>();
        searchHistory = new ArrayList<>();
        fillUpSearchSuggestions();
        fillUpSearchHistory();

        // init search history RecyclerView
        rvSearchHistory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        searchHistoryRVAdapter = new SearchHistoryRVAdapter(getActivity(), searchHistory);
        searchHistoryRVAdapter.setClickListener(this);
        rvSearchHistory.setAdapter(searchHistoryRVAdapter);

        // init search suggestions RecyclerView
        // rvSearchSuggestions.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        rvSearchSuggestions.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        searchSuggestionRVAdapter = new SearchSuggestionRVAdapter(getActivity(), searchSuggestions);
        searchSuggestionRVAdapter.setClickListener(this);
        rvSearchSuggestions.setAdapter(searchSuggestionRVAdapter);

        // show keyboard
        // search.requestFocus();
        // InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        // imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        // search filter icon press
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // display filter bottom sheet
                hideKeyboardIfVisible();
                SearchFilterBottomSheet bottomSheet = new SearchFilterBottomSheet();
                bottomSheet.show(getActivity().getSupportFragmentManager(), "ModalBottomSheet");
            }
        });

        return layout;
    }

    @Override
    public void onSearchSuggestionItemClick(View view, int pos) {
        search.setText(searchSuggestions.get(pos) + " ");
        search.setSelection(search.getText().length());
    }

    @Override
    public void onSearchHistoryItemClick(View view, int pos) {
        search.setText(searchHistory.get(pos) + " ");
        search.setSelection(search.getText().length());
    }

    private void fillUpSearchSuggestions() {
        searchSuggestions.add("sushi");
        searchSuggestions.add("sandwich");
        searchSuggestions.add("seafood");
        searchSuggestions.add("fried rice");
    }

    private void fillUpSearchHistory() {
        searchHistory.add("pancakes");
        searchHistory.add("salad");
        searchHistory.add("lsd");
    }

    private void preventFlickering() {
        // prevent navbars from flickering on transitions
        Fade fade = new Fade();
        View decor = getActivity().getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.bottomAppBar), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getActivity().getWindow().setEnterTransition(fade);
        getActivity().getWindow().setExitTransition(fade);
    }

    private void hideKeyboardIfVisible() {
        // hide keyboard if visible
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
        }
    }

}