package com.prem.android.bakingtime.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.prem.android.bakingtime.R;

import adapters.RecipeStepsAdapter;
import fragment.StepsDetailFragment;
import fragment.StepsToMakeRecipe;
import models.Recipe;
import models.Step;
import utils.Constants;

public class RecipeSteps extends AppCompatActivity implements RecipeStepsAdapter.RecViewListener {

    private Recipe mRecipe;
    private int mSelectedStepPosition;
    private boolean mTwoPaneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        if (savedInstanceState == null) {
            if (getIntent().getParcelableExtra(Constants.SELECTED_RECIPE) != null) {
                mRecipe = getIntent().getParcelableExtra(Constants.SELECTED_RECIPE);
            } else {
                Toast.makeText(this, "No data obtained", Toast.LENGTH_LONG).show();
            }
        } else {
            mRecipe = savedInstanceState.getParcelable(Constants.RECIPE_OBJECT);
        }
        getSupportActionBar().setTitle(mRecipe.getName());

        // get the fragment manager to handle transaction
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (findViewById(R.id.select_recipe_two_pane_layout) != null) {
            mTwoPaneLayout = true;

            //In two pane mode, add initial fragments
            StepsToMakeRecipe fragmentRecipe = new StepsToMakeRecipe();
            fragmentRecipe.provideContext(this);
            fragmentRecipe.provideRecipeDetails(mRecipe);
            fragmentManager.beginTransaction()
                    .add(R.id.view_holder_for_steps_detail, fragmentRecipe)
                    .commit();

            StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
            stepsDetailFragment.provideContext(this);
            Step mStep = mRecipe.getSteps().get(mSelectedStepPosition);
            stepsDetailFragment.provideData(mStep.getVideoURL(), mStep.getDescription(), mStep.getThumbnailURL());
            fragmentManager.beginTransaction()
                    .add(R.id.view_holder_for_videos_steps, stepsDetailFragment)
                    .commit();

        } else {
            mTwoPaneLayout = false;
            StepsToMakeRecipe fragmentRecipe = new StepsToMakeRecipe();
            fragmentRecipe.provideContext(this);
            fragmentRecipe.provideRecipeDetails(mRecipe);
            fragmentManager.beginTransaction()
                    .add(R.id.view_holder_for_steps_detail, fragmentRecipe)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.RECIPE_OBJECT, mRecipe);
        outState.putInt("PositionSelected", mSelectedStepPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStepClicked(int positionOfSelectedStep) {
        mSelectedStepPosition = positionOfSelectedStep;
    }
}
