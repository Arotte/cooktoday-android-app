package com.animee.cooktodaynew.recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.animee.cooktodaynew.R;
import com.animee.cooktodaynew.bean.FoodBean;

import java.util.List;


public class RecipeAdapter extends BaseAdapter {
    Context context;
    List<FoodBean>mDatas;

    public RecipeAdapter(Context context, List<FoodBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

// Decide ListView number of lines
    @Override
    public int getCount() {
        return mDatas.size();
    }

// data corresponding to position
    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

// return id of position
    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        ImageView iv;
        TextView titleTv, ingredientsTv;
        public ViewHolder(View view){
            iv = view.findViewById(R.id.item_recipe_iv);
            titleTv = view.findViewById(R.id.item_recipe_tv_title);
            ingredientsTv = view.findViewById(R.id.item_recipe_tv_ingredients);
        }
    }

// View corresponding to the position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            // the way of layout convert to object
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recipe_lv,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        // Loading controls to display content
        FoodBean foodBean = mDatas.get(position);
        holder.titleTv.setText(foodBean.getName());
        holder.ingredientsTv.setText("Ingredients: "+foodBean.getIngredients());
        //holder.iv.setImageResource(foodBean.getPicId());
        return convertView;
    }


}






















