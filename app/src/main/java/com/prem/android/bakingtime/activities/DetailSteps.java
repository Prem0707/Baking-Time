package com.prem.android.bakingtime.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.prem.android.bakingtime.R;

import java.util.ArrayList;

import fragment.StepsDetailFragment;
import models.Step;
import utils.Constants;

public class DetailSteps extends AppCompatActivity {

    private ArrayList<Step> mRecipeSteps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_steps);

        //Create StepsDetailFragment
        StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
        stepsDetailFragment.provideContext(this);

        if(getIntent().getParcelableExtra(Constants.STEP_TO_MAKE) != null){
            mRecipeSteps = getIntent().getParcelableExtra(Constants.SELECTED_RECIPE);
            //getSupportActionBar().setTitle(mRecipe.getName());
            stepsDetailFragment.provideData(mRecipeSteps);
        }else{
            Toast.makeText(this, "No data obtained", Toast.LENGTH_LONG);
        }

        //add the fragment to its container using fragmentmanager and transaction
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.view_holder_for_steps_detail, stepsDetailFragment)
                .commit();
    }
}
