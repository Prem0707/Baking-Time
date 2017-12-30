package sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Prem on 30-12-2017.
 */

public class PrefForSteps {
    public static void setSharedPref(int value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("VIDEOS_STEP_SELECTED", value);
        editor.apply();
    }

    public static int getSharedPref(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt("VIDEOS_STEP_SELECTED", 0);
    }
}
