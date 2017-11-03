package com.prem.android.bakingtime.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.prem.android.bakingtime.R;

import fragment.StepsDetailFragment;

public class DetailSteps extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_steps);

        //Create StepsDetailFragment
        StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
        stepsDetailFragment.provideContext(this);

        //add the fragment to its container using fragmentmanager and transaction
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.view_holder_for_steps_detail, stepsDetailFragment)
                .commit();
    }
}
