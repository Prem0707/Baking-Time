package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prem.android.bakingtime.R;

import java.util.ArrayList;

import interfaces.TaskCompleted;
import models.Recipe;

/**
 * A simple fragment which will contain the MainRecipe cards.
 */
public class SelectRecipe extends Fragment implements TaskCompleted{


    ArrayList<Recipe> recipeList;
    public SelectRecipe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_recipe, container, false);
    }

     //Called when the Fragment is visible to the user
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onTaskCompleted(ArrayList<Recipe> mRecipe) {
        this.recipeList = mRecipe;
    }
}
