package extras;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.prem.android.bakingtime.R;

/**
 * Created by Prem on 30-10-2017.
 */

public class RecipeImageProvider {

    static int[] intArray = new int[]{R.drawable.nutella_pie, R.drawable.brownies, R.drawable.yellow_cake, R.drawable.cheese_cake};

    public static Bitmap provideRecipeImage(Resources resources, int index) {

        //setting the bitmap from the drawable folder
        Bitmap bitmap = BitmapFactory.decodeResource(resources, intArray[index]);
        return bitmap;
    }

}
