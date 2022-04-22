package com.abdn.cooktoday.local_data.model;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;

import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.ingred_ner.IngredientNerJson;

public class NerredIngred {

    private String original;
    private String quantity;
    private String unit;
    private String name;
    private String nil;

    // tag indexes in original string
    private int quantityStart;
    private int unitStart;
    private int nameStart;

    public NerredIngred(IngredientNerJson nerJson) {
        this.original = nerJson.getOriginal();
        this.quantity = nerJson.getTags().getQuantity();
        this.unit = nerJson.getTags().getUnit();
        this.name = nerJson.getTags().getName();
        this.nil = nerJson.getTags().getNil();
        setIndexes();
    }

    public NerredIngred(String original, String quantity, String unit, String name) {
        this.original = original;
        this.quantity = quantity;
        this.unit = unit;
        this.name = name;
        setIndexes();
    }

    public void colorize(EditText et, int quantityColor, int unitColor, int nameColor) {
        et.setText(getSpannableIngredStr(quantityColor, unitColor, nameColor));
    }

    public void colorize(TextView tv, int quantityColor, int unitColor, int nameColor) {
        tv.setText(getSpannableIngredStr(quantityColor, unitColor, nameColor));
    }

    private Spannable getSpannableIngredStr(int quantityColor, int unitColor, int nameColor) {
        Spannable spannableIngredStr = new SpannableString(original);

        if (quantity != null)
            spannableIngredStr.setSpan(new BackgroundColorSpan(quantityColor),
                    quantityStart,
                    quantityStart + quantity.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (unit != null)
            spannableIngredStr.setSpan(new BackgroundColorSpan(unitColor),
                    unitStart,
                    unitStart + unit.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (name != null)
            spannableIngredStr.setSpan(new BackgroundColorSpan(nameColor),
                    nameStart,
                    nameStart + name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableIngredStr;
    }

    private void setIndexes() {
        if (quantity != null)
            this.quantityStart = this.original.indexOf(this.quantity);
        if (unit != null)
            this.unitStart = this.original.indexOf(this.unit);
        if (name != null)
            this.nameStart = this.original.indexOf(this.name);
    }

    @Override
    public String toString() {
        return "NerredIngred{" +
                "original='" + original + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unit='" + unit + '\'' +
                ", name='" + name + '\'' +
                ", nil='" + nil + '\'' +
                '}';
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
