package com.prem.android.bakingtime.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.prem.android.bakingtime.R;

import fragment.StepsToMakeRecipe;
import models.Recipe;
import utils.Constants;

public class RecipeSteps extends AppCompatActivity {

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_and);

        //create SelectRecipe fragment
        StepsToMakeRecipe fragmentRecipe = new StepsToMakeRecipe();
        fragmentRecipe.provideContext(this);

        if(getIntent().getParcelableExtra(Constants.SELECTED_RECIPE) != null){
            mRecipe = getIntent().getParcelableExtra(Constants.SELECTED_RECIPE);
            getSupportActionBar().setTitle(mRecipe.getName());
            fragmentRecipe.provideRecipeDetails(mRecipe);
        }else{
            Toast.makeText(this, "No data obtained", Toast.LENGTH_LONG).show();
        }

        //add the fragment to its container using fragment manager and transaction
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.view_holder_for_ingredients_steps, fragmentRecipe)
                .commit();
    }
}
