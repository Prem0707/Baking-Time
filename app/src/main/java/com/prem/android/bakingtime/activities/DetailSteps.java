package com.prem.android.bakingtime.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.prem.android.bakingtime.R;

import fragment.StepsDetailFragment;
import models.Step;
import utils.Constants;

public class DetailSteps extends AppCompatActivity {

    private Step mRecipeStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_steps);

        //Create StepsDetailFragment
        StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
        stepsDetailFragment.provideContext(this);

        if (savedInstanceState == null) {
            if (getIntent().getParcelableExtra(Constants.STEP_TO_MAKE) != null) {
                mRecipeStep = getIntent().getParcelableExtra(Constants.STEP_TO_MAKE);
            } else {
                savedInstanceState.getParcelable("STEP_DETAILS");
            }

            if (mRecipeStep != null) {
                stepsDetailFragment.provideData(mRecipeStep.getVideoURL(), mRecipeStep.getDescription(),
                        mRecipeStep.getThumbnailURL());
            } else {
                Toast.makeText(this, "NO Data Passed to Detail Step", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "No data obtained", Toast.LENGTH_LONG);
        }


        //add the fragment to its container using fragment manager and transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.view_holder_for_steps_detail, stepsDetailFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("STEP_DETAILS", mRecipeStep);
    }
}
