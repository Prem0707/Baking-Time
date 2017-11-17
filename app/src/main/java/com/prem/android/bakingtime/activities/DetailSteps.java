package com.prem.android.bakingtime.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.prem.android.bakingtime.R;

import fragment.StepsVideoFragment;
import models.Step;
import utils.Constants;

public class DetailSteps extends AppCompatActivity {

    private Step mRecipeStep;
    StepsVideoFragment stepsDetailFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_steps);

        if (savedInstanceState == null) {
            if (getIntent().getParcelableExtra(Constants.STEP_TO_MAKE) != null) {
                mRecipeStep = getIntent().getParcelableExtra(Constants.STEP_TO_MAKE);
            } else {
                mRecipeStep = savedInstanceState.getParcelable("STEP_DETAILS");
            }

            if (savedInstanceState == null) {
                //Create StepsVideoFragment
                stepsDetailFragment = new StepsVideoFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("DATA_SENT_TO_VIDEO_FRAG", mRecipeStep);
                stepsDetailFragment.setArguments(bundle);

                //add the fragment to its container using fragment manager and transaction
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.view_holder_for_steps_detail, stepsDetailFragment)
                        .commit();
            }else{
                stepsDetailFragment =(StepsVideoFragment) getSupportFragmentManager()
                        .getFragment(savedInstanceState, "VIDEO_FRAG");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("STEP_DETAILS", mRecipeStep);
        getSupportFragmentManager().putFragment(outState,
                "VIDEO_FRAG",stepsDetailFragment );
    }
}


