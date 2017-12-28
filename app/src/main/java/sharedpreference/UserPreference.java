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

    private static String RECIPE_TO_SHOW = "ingredients_to_show";

    public static void setSharedPref(Recipe mRecipe, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mRecipe); // Converting json to string
        editor.putString(RECIPE_TO_SHOW, json);
        editor.apply();
    }

    public static Recipe getSharedPref(Context context) {
        Gson gson = new Gson();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String json = preferences.getString(RECIPE_TO_SHOW, "Nothing Here to Show");
        return gson.fromJson(json, Recipe.class);
    }
}
