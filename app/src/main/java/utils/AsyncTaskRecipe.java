package utils;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import interfaces.TaskCompleted;
import json_parser.recipeJsonParcer;
import models.Recipe;

/**
 * Created by Prem on 29-10-2017.
 */

public class AsyncTaskRecipe extends AsyncTask<Void, Void, ArrayList<Recipe>> {

    private final TaskCompleted mCallback;

    public AsyncTaskRecipe(Context context) {
        this.mCallback = (TaskCompleted) context;

    }



    @Override
    protected ArrayList<Recipe> doInBackground(Void... voids) {


        URL urlForRecipe = NetworkUtils.buildURL();

        if (urlForRecipe != null) {
            try {
                String responseForRecipe = null;
                try {
                    responseForRecipe = NetworkUtils.getResponseFromHttpUrl(urlForRecipe);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return recipeJsonParcer.getRecipeData(responseForRecipe);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(ArrayList<Recipe> mRecipe) {
        super.onPostExecute(mRecipe);
        //This is where you return data back to caller
        mCallback.onTaskCompleted(mRecipe);
    }
}
