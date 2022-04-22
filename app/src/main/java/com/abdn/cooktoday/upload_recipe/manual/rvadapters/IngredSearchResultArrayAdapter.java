package com.abdn.cooktoday.upload_recipe.manual.rvadapters;

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
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.IngredSearchResultItemJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe_search.RecipeSearchResultItemJSON;

import java.util.ArrayList;
import java.util.List;

public class IngredSearchResultArrayAdapter extends ArrayAdapter<IngredSearchResultItemJson> {

    private final Context mContext;
    private List<IngredSearchResultItemJson> ingredsList;

    public IngredSearchResultArrayAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes List<IngredSearchResultItemJson> list) {
        super(context, 0 , list);
        mContext = context;
        ingredsList = new ArrayList<>();
        ingredsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
        listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_add_recipe_ingred_search_item, parent, false);

        IngredSearchResultItemJson currentRecipe = ingredsList.get(position);
        TextView name = (TextView) listItem.findViewById(R.id.tvSearchResultRecipeName);
        name.setText(currentRecipe.getName());

        return listItem;
    }

    public IngredSearchResultItemJson getItem(int position){
        return ingredsList.get(position);
    }
}