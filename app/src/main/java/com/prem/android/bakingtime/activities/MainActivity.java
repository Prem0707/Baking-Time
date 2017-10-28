package com.prem.android.bakingtime.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.prem.android.bakingtime.R;

import java.util.ArrayList;

import fragment.SelectRecipe;
import interfaces.TaskCompleted;
import models.Recipe;
import utils.AsyncTaskRecipe;
import utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements TaskCompleted{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create SelectRecipe fragment
        SelectRecipe fragmentRecipe = new SelectRecipe();

        //add the fragment to its container using fragmentmanager and transaction
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.view_holder_for_select_recipe, fragmentRecipe)
                .commit();

        //Initialisation of AsyncTask to get raw data
        AsyncTaskRecipe asyncTaskRecipe = new AsyncTaskRecipe(MainActivity.this);
        asyncTaskRecipe.execute();

        Toast.makeText(this, NetworkUtils.buildURL().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTaskCompleted(ArrayList<Recipe> mRecipe) {

    }
}
