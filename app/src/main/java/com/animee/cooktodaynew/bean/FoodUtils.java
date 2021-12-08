// will change when get recipe database
package com.animee.cooktodaynew.bean;


import android.content.Context;
import android.renderscript.ScriptGroup;

import com.animee.cooktodaynew.recipe.RecipeActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;



public class FoodUtils {
    private static  FoodUtils instance;
    private FoodUtils(){

    }
    public  static FoodUtils getInstance(){
        if(instance == null){
            instance = new FoodUtils();
        }
        return instance;
    }
    private String read(InputStream is){
        BufferedReader reader = null;
        StringBuilder sb = null;

        String line = null;

        try{
            sb= new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null){
                sb.append(line);
                sb.append("\n");
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            try{
                if(reader!=null) reader.close();
                if(is!=null) is.close();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return sb.toString();
    }

    public List<FoodBean> getAllFoodList(Context context){
        List<FoodBean> list = new ArrayList<>();
        InputStream is = null;
        try{
            is = context.getResources().getAssets().open("cooktoday.json");
            String json = read(is);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<FoodBean>>(){}.getType();
            // use gson parsing json and keep in list
            list = gson.fromJson(json,listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if(is!=null) is.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }


        return list;
    }



}
