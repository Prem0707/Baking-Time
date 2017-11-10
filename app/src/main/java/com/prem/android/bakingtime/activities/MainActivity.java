package com.prem.android.bakingtime.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.prem.android.bakingtime.R;

import fragment.SelectRecipe;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            //create SelectRecipe fragment
            SelectRecipe fragmentRecipe = new SelectRecipe();
            fragmentRecipe.provideContext(this);

            //add the fragment to its container using fragment manager and transaction
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.view_holder_for_select_recipe, fragmentRecipe)
                    .commit();
        }
    }
}
