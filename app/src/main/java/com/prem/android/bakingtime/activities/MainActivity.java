package com.prem.android.bakingtime.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.prem.android.bakingtime.R;

import java.util.ArrayList;

import adapters.RecipeAdapter;
import extras.BasicUtility;
import interfaces.TaskCompleted;
import models.Recipe;
import sharedpreference.UserPreference;
import utils.AsyncTaskRecipe;
import utils.Constants;
import utils.NetworkUtils;
import widget.WidgetDataProvider;

public class MainActivity extends AppCompatActivity implements TaskCompleted,
        RecipeAdapter.RecyclerViewClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private RecipeAdapter adapter;
    private ProgressBar spinner;
    ArrayList<Recipe> recipeList;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (ProgressBar) findViewById(R.id.progress_bar);
        spinner.setVisibility(View.VISIBLE);

        mRecyclerView = (RecyclerView) findViewById(R.id.recipe_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mGridLayoutManager = BasicUtility.gridLayoutManagerAccordingToOrientation(this);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        adapter = new RecipeAdapter(this, MainActivity.this);

        if (savedInstanceState != null) {
            this.recipeList = savedInstanceState.getParcelableArrayList(Constants.RECIPE_LIST);
            adapter.setDataset(recipeList);
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable("BUNDLE_RECYCLER_LAYOUT");
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            Toast.makeText(this, "Using Already Save Data", Toast.LENGTH_LONG).show();

        } else {
            callToAsyncTask(this);
        }
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.RECIPE_LIST, recipeList);
        //save the current recyclerView position
        outState.putParcelable("BUNDLE_RECYCLER_LAYOUT", mRecyclerView.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onTaskCompleted(ArrayList<Recipe> mRecipe) {
        this.recipeList = mRecipe;
        // now recipeList has data
        adapter.setDataset(recipeList);
        spinner.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRecipeClick(int positionOfSelectedRecipe) {
        UserPreference.setSharedPref(positionOfSelectedRecipe, this);
        Intent recipeSteps = new Intent(this, RecipeSteps.class);
        recipeSteps.putExtra(Constants.SELECTED_RECIPE, (Parcelable) recipeList.get(positionOfSelectedRecipe));
        startActivity(recipeSteps);
    }

    private void callToAsyncTask(Context context) {
        boolean checkInternetConnection = NetworkUtils.checkDeviceOnline(this);
        if (checkInternetConnection) {
            AsyncTaskRecipe asyncTaskRecipe = new AsyncTaskRecipe(this);
            asyncTaskRecipe.execute();
        } else {
            Toast.makeText(this, "Check Your Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Cleanup the shared preference listener
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
           int positionOdRecipe = UserPreference.getSharedPref(this);
           Recipe mRecipe = recipeList.get(positionOdRecipe);
           new WidgetDataProvider(mRecipe);
    }
}
