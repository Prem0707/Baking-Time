package interfaces;

import java.util.ArrayList;

import models.Recipe;

/**
 * Created by Prem on 29-10-2017.
 * Whenever we need to call to another thread and expecting some result return you need to implement this interface
 */

public interface TaskCompleted {

    // Define data you like to return from AsyncTask
    void onTaskCompleted(ArrayList<Recipe> mRecipe);
}
