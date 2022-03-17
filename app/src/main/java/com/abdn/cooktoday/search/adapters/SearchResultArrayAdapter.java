package com.abdn.cooktoday.search.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe_search.RecipeSearchResultItemJSON;

import java.util.ArrayList;
import java.util.List;

public class SearchResultArrayAdapter extends ArrayAdapter<RecipeSearchResultItemJSON> {

    private final Context mContext;
    private List<RecipeSearchResultItemJSON> recipesList;

    public SearchResultArrayAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<RecipeSearchResultItemJSON> list) {
        super(context, 0 , list);
        mContext = context;
        recipesList = new ArrayList<>();
        recipesList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
        listItem = LayoutInflater.from(mContext).inflate(R.layout.fragment_search_layout_item_autocomplete, parent, false);

        RecipeSearchResultItemJSON currentRecipe = recipesList.get(position);

//        ImageView image = (ImageView) listItem.findViewById(R.id.ivSearchResultRecipeImage);
//        Picasso.get().load(currentRecipe.getImgUrl()).into(image);

        TextView name = (TextView) listItem.findViewById(R.id.tvSearchResultRecipeName);
        name.setText(currentRecipe.getName());

        return listItem;
    }

    public RecipeSearchResultItemJSON getItem(int position){
        return recipesList.get(position);
    }
}