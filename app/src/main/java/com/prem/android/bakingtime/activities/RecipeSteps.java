package com.prem.android.bakingtime.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.prem.android.bakingtime.R;

import adapters.RecipeStepsAdapter;
import fragment.StepsToMakeRecipe;
import fragment.StepsVideoFragment;
import interfaces.Communicator;
import models.Recipe;
import models.Step;
import utils.Constants;

public class RecipeSteps extends AppCompatActivity implements RecipeStepsAdapter.RecViewListener, Communicator {

    private Recipe mRecipe;
    private int mSelectedStepPosition;
    private boolean mTwoPaneLayout;
    private String mActionBarName;
    private StepsToMakeRecipe fragmentRecipe;
    private StepsVideoFragment stepsDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        if (savedInstanceState == null) {
            if (getIntent().getParcelableExtra(Constants.SELECTED_RECIPE) != null)
                mRecipe = getIntent().getParcelableExtra(Constants.SELECTED_RECIPE);
                mActionBarName = mRecipe.getName();
        } else {
            mRecipe = savedInstanceState.getParcelable(Constants.RECIPE_OBJECT);
            mActionBarName = savedInstanceState.getString("ACTION_BAR_NAME");
        }
        getSupportActionBar().setTitle(mActionBarName);

        // get the fragment manager to handle transaction
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (findViewById(R.id.select_recipe_two_pane_layout) != null) {
            mTwoPaneLayout = true;

            //In two pane mode, add initial fragments
            fragmentRecipe = new StepsToMakeRecipe();
            fragmentManager.beginTransaction()
                    .add(R.id.view_holder_for_steps_detail, fragmentRecipe)
                    .commit();

            stepsDetailFragment = new StepsVideoFragment();
            Step mStep = mRecipe.getSteps().get(mSelectedStepPosition);
            fragmentManager.beginTransaction()
                    .add(R.id.view_holder_for_videos_steps, stepsDetailFragment)
                    .commit();

        } else {
            mTwoPaneLayout = false;

            if (savedInstanceState != null) {
                fragmentRecipe = (StepsToMakeRecipe) getSupportFragmentManager()
                        .getFragment(savedInstanceState, "RECIPE_FRAG");
            } else {
                fragmentRecipe = new StepsToMakeRecipe();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("DATA_TO_STEP_TO_MAKE_RECIPE",  mRecipe.getSteps());
                fragmentRecipe.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .add(R.id.view_holder_for_steps_detail, fragmentRecipe)
                        .commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.RECIPE_OBJECT, mRecipe);
        outState.putInt("SELECT_RECIPE_DETAIL", mSelectedStepPosition);
        outState.putString("ACTION_BAR_NAME", mActionBarName);
        getSupportFragmentManager().putFragment(outState,
                "RECIPE_FRAG", fragmentRecipe);
    }

    @Override
    public void onStepClicked(int positionOfSelectedStep) {
        mSelectedStepPosition = positionOfSelectedStep;
    }

    @Override
    public void respond() {

    }
}
