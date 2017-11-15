package utils;

import android.content.res.Configuration;
import android.content.res.Resources;

/**
 * Created by Prem on 15-11-2017.
 */

public class BasicUtility {

    //Checking the either app is running on tablet or not
    public static boolean tabletMode() {
        Configuration resources = Resources.getSystem().getConfiguration();
        return (resources.smallestScreenWidthDp >= 600
                && resources.orientation == Configuration.ORIENTATION_LANDSCAPE);
    }
}
