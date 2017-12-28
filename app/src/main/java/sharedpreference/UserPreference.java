package sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import models.Recipe;

/**
 * Created by Prem on 20-12-2017.
 */

public class UserPreference {

    public static String RECIPE_TO_SHOW= "ingredients_to_show";
    private static SharedPreferences preferences;

    public static void setSharedPref(Recipe mRecipe, Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mRecipe); // Converting json to string
        editor.putString(RECIPE_TO_SHOW, json);
        editor.apply();
    }

    public static Recipe getSharedPref() {
        Gson gson = new Gson();
        String json= preferences.getString(RECIPE_TO_SHOW, "Nothing Here to Show");
        Recipe recipe = gson.fromJson(json, Recipe.class);
        return recipe;
        //0 is default parameter
    }
}
