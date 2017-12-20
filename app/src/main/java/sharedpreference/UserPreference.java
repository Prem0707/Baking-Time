package sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Prem on 20-12-2017.
 */

public class UserPreference {

    public static String INDEX_OF_RECIPE= "wideget_to_show";

    public static void setSharedPref(int recipeToShow, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(INDEX_OF_RECIPE, recipeToShow);
        editor.apply();
    }

    public static int getSharedPref(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(INDEX_OF_RECIPE, 0);
        //0 is default parameter
    }
}
