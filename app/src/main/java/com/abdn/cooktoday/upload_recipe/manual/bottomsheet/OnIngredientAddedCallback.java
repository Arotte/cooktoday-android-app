package com.abdn.cooktoday.upload_recipe.manual.bottomsheet;

import com.abdn.cooktoday.local_data.model.NerredIngred;

public interface OnIngredientAddedCallback {
    void success(NerredIngred ingred);
}
