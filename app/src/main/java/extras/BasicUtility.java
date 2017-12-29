package extras;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.widget.GridLayoutManager;

import models.Ingredient;

/**
 * Created by Prem on 10-11-2017.
 * This class provides all the basic methods throughout this projects
 */


public class BasicUtility {

    //Checking the either app is running on tablet or not
    public static boolean tabletMode() {
        Configuration resources = Resources.getSystem().getConfiguration();
        return (resources.smallestScreenWidthDp >= 600);
    }

    public static GridLayoutManager gridLayoutManagerAccordingToOrientation(Context mContext) {
        int deviceOrientation = checkDeviceOrientation(mContext);

        if (deviceOrientation == Configuration.ORIENTATION_PORTRAIT) {
            return new GridLayoutManager(mContext, 1);
        } else
            return new GridLayoutManager(mContext, 2);
    }

    private static int checkDeviceOrientation(Context context) {
        return context.getResources().getConfiguration().orientation;
    }

    public static String DataToShow(Ingredient mIngredient) {

        String ingredientValue = mIngredient.getIngredient();
        String measure = mIngredient.getMeasure();
        String quantity = Double.toString(mIngredient.getQuantity());
        String textToShow = ingredientValue + " -   " + quantity + measure;

        return textToShow;
    }
}
