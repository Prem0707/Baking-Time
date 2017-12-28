package com.prem.android.bakingtime.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.prem.android.bakingtime.R;

import adapters.RecipeStepsAdapter;
import extras.BasicUtility;
import fragment.StepsToMakeRecipe;
import fragment.StepsVideoFragment;
import models.Recipe;
import models.Step;
import utils.Constants;

public class RecipeSteps extends AppCompatActivity implements RecipeStepsAdapter.RecViewListener,
        StepsToMakeRecipe.OnHeadlineSelectedListener {

    private Recipe mRecipe;
    private int mSelectedStepPosition;
    private String mActionBarName;
    private StepsToMakeRecipe fragmentRecipe;
    private StepsVideoFragment stepsDetailFragment;
    Bundle bundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        android.support.v7.app.ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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

        if (savedInstanceState == null || fragmentManager == null) {
            fragmentRecipe = new StepsToMakeRecipe();
            bundle.putParcelableArrayList("DATA_TO_STEP_TO_MAKE_RECIPE", mRecipe.getSteps());
            Toast.makeText(this, "Created Recipe fragment", Toast.LENGTH_LONG).show();
            fragmentRecipe.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.view_holder_for_steps_detail, fragmentRecipe)
                    .commit();
        } else {
            fragmentRecipe = (StepsToMakeRecipe) getSupportFragmentManager()
                    .getFragment(savedInstanceState, "RECIPE_FRAG");
            Toast.makeText(this, " fragment", Toast.LENGTH_LONG).show();
        }


        if (BasicUtility.tabletMode()) {
            if (savedInstanceState == null || stepsDetailFragment == null) {
                //In two pane mode, add detail fragment fragments
                stepsDetailFragment = new StepsVideoFragment();
                Toast.makeText(this, "Created new Video fragment", Toast.LENGTH_LONG).show();
                Step mStep = mRecipe.getSteps().get(mSelectedStepPosition);
                bundle.putParcelable("DATA_SENT_TO_VIDEO_FRAG", mStep);
                stepsDetailFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.view_holder_for_videos_steps, stepsDetailFragment)
                        .commit();
            } else {
                Toast.makeText(this, "Using already Saved Video fragment", Toast.LENGTH_LONG).show();
                stepsDetailFragment = (StepsVideoFragment) getSupportFragmentManager()
                        .getFragment(savedInstanceState, "VIDEO_FRAG");
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
        if(BasicUtility.tabletMode() && stepsDetailFragment != null){
            Toast.makeText(this, "Saved Video fragment", Toast.LENGTH_LONG).show();
            getSupportFragmentManager().putFragment(outState,
                    "VIDEO_FRAG", stepsDetailFragment);
        }
    }

    @Override
    public void onStepClicked(int positionOfSelectedStep) {
        mSelectedStepPosition = positionOfSelectedStep;
    }

    @Override
    public void onArticleSelected(Step stepsOfRecipe) {
        bundle.putParcelable("DATA_SENT_TO_VIDEO_FRAG", stepsOfRecipe);
        stepsDetailFragment.setArguments(bundle);
    }
}
