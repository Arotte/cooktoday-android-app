package com.abdn.cooktoday.upload_recipe.manual.dialog;

import com.abdn.cooktoday.local_data.model.Ingredient;

public interface AddIngredDialogCallback {
    void added(Ingredient ingredient);
    void cancelled();
}
