package extras;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.GridLayoutManager;

/**
 * Created by Prem on 10-11-2017.
 * This class provides all the basic methods throughout this projects
 */


public class BasicUtility {

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
}
