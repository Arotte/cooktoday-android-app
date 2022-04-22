package com.abdn.cooktoday.search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.api_connection.Server;
import com.abdn.cooktoday.api_connection.ServerCallbacks;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe_search.RecipeSearchResultItemJSON;
import com.abdn.cooktoday.local_data.Cache;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.recipedetails.RecipeDetailsActivity;
import com.abdn.cooktoday.search.adapters.SearchResultArrayAdapter;
import com.abdn.cooktoday.search.bottomsheet.SearchFilterBottomSheet;
import com.abdn.cooktoday.search.adapters.SearchHistoryRVAdapter;
import com.abdn.cooktoday.search.adapters.SearchSuggestionRVAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment
        implements
            SearchSuggestionRVAdapter.ItemClickListener,
            SearchHistoryRVAdapter.ItemClickListener {
    private static final String TAG = "SearchFragment";

    String userSessId;

    EditText searchField;
    ImageView filter;

    SearchSuggestionRVAdapter searchSuggestionRVAdapter;
    SearchHistoryRVAdapter searchHistoryRVAdapter;
    List<String> searchHistory;
    List<String> searchSuggestions;

    LinearLayout initialScreenContainer;
    LinearLayout searchResultViewContainer;
    ListView searchResultsContainer;
    SearchResultArrayAdapter searchResultsAdapter;

    private int prevSearchFieldLen;

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

        // get saved session ID of logged in user
        Cache.init(getActivity());
        userSessId = Cache.read_string(Cache.KEY_USER_SESSID, "");

        // init elements
        searchField = (EditText) layout.findViewById(R.id.searchBar);
        filter = layout.findViewById(R.id.searchFilterIcon);
        filter.setVisibility(View.GONE);
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
        searchField.requestFocus();
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

        //
        initialScreenContainer = layout.findViewById(R.id.llSearchInitialScreenContainer);
        searchResultViewContainer = layout.findViewById(R.id.llSearchAutocompleteSuggestions);
        toggleViews(true);

        // watch search field
        prevSearchFieldLen = 0;
        searchField.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                int len = s.length();
                if (prevSearchFieldLen == 0 && len > 0) // user typed first character
                    toggleViews(false);
                if (prevSearchFieldLen > 0 && len == 0) // user deleted last character
                    toggleViews(true);
                if (len >= 2) // at least 2 characters are present
                    search(s.toString());
                else { // init result list
                    updateResultList(new ArrayList<RecipeSearchResultItemJSON>());
                }

                prevSearchFieldLen = len;
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // set listener for icon
        searchField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (searchField.getRight() - searchField.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        searchField.setText("");
                        return true;
                    }
                }
                return false;
            }
        });

        searchResultsContainer = layout.findViewById(R.id.lvSearchResults);

        return layout;
    }

    private void search(String query) {
        // send search request to server
        Server.searchRecipes(userSessId, query, new ServerCallbacks.RecipeSearchResult() {
            @Override
            public void success(ArrayList<RecipeSearchResultItemJSON> recipes) {
                Log.i(TAG, "Recipe search successful!");
                // got search result from server
                // update our array adapter
                updateResultList(recipes);
            }

            @Override
            public void error(int errorCode) {
                // error during search
                Log.i(TAG, "Error during search (code: " + errorCode + "). User session id='" + userSessId + "'");
            }
        });
    }

    private void updateResultList(ArrayList<RecipeSearchResultItemJSON> newItems) {
        searchResultsAdapter = new SearchResultArrayAdapter(getActivity(), newItems);
        searchResultsContainer.setAdapter(searchResultsAdapter);

        // set onclick listeners
        if (newItems.size() > 0) {
            searchResultsContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // get recipe details from server
                    Server.getRecipeById(userSessId, searchResultsAdapter.getItem(i).getId(), new ServerCallbacks.GetRecipeResult() {
                        @Override
                        public void success(Recipe recipe) {
                            // show recipe
                            Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
                            intent.putExtra("RecipeObject", recipe);
                            startActivity(intent);
                        }

                        @Override
                        public void error(int errorCode) {
                            Log.i(TAG, "Could not get recipe '" + errorCode + "'.");
                        }
                    });
                }
            });
        }
    }

    private void toggleViews(boolean showInitialSearchScreen) {
        if (showInitialSearchScreen) {
            initialScreenContainer.setVisibility(View.VISIBLE);
            searchResultViewContainer.setVisibility(View.INVISIBLE);
        } else {
            initialScreenContainer.setVisibility(View.INVISIBLE);
            searchResultViewContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSearchSuggestionItemClick(View view, int pos) {
        searchField.setText(searchSuggestions.get(pos) + " ");
        searchField.setSelection(searchField.getText().length());
    }

    @Override
    public void onSearchHistoryItemClick(View view, int pos) {
        searchField.setText(searchHistory.get(pos) + " ");
        searchField.setSelection(searchField.getText().length());
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
            imm.hideSoftInputFromWindow(searchField.getWindowToken(), 0);
        }
    }

    private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain", "Itabi"
    };

}