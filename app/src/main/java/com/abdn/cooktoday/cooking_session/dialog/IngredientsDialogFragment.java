package com.abdn.cooktoday.cooking_session.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.cooking_session.rvadapters.IngredientsRVAdapter;
import com.abdn.cooktoday.local_data.model.Ingredient;

import java.util.List;

public class IngredientsDialogFragment extends DialogFragment {
    public static final String TAG = "IngredientsDialog";

    private final List<Ingredient> ingredients;

    public IngredientsDialogFragment(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cooking_sess_dialog_ingredients_panel, container, false);
        // Set transparent background and no title
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        RecyclerView recyclerView = view.findViewById(R.id.rvCookingSessIngreds);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        IngredientsRVAdapter adapter = new IngredientsRVAdapter(getContext(), ingredients);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
