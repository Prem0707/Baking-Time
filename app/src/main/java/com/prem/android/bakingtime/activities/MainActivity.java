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
import butterknife.BindView;
import butterknife.ButterKnife;
import extras.BasicUtility;
import interfaces.TaskCompleted;
import models.Recipe;
import sharedpreference.UserPreference;
import utils.AsyncTaskRecipe;
import utils.Constants;
import utils.NetworkUtils;
import widgets.IngredientsWidgets;

public class MainActivity extends AppCompatActivity implements TaskCompleted,
        RecipeAdapter.RecyclerViewClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private RecipeAdapter adapter;
    @BindView(R.id.progress_bar) ProgressBar spinner;
    private int positionForWidget;
    private static ArrayList<Recipe> recipeList;
    @BindView(R.id.recipe_recyclerView) RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this); //Bind butterknife with MainActivity

        spinner.setVisibility(View.VISIBLE);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mGridLayoutManager = BasicUtility.gridLayoutManagerAccordingToOrientation(this);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        adapter = new RecipeAdapter(this, MainActivity.this);

        if (savedInstanceState != null) {
            spinner.setVisibility(View.INVISIBLE);
            recipeList = savedInstanceState.getParcelableArrayList(Constants.RECIPE_LIST);
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
        recipeList = mRecipe;
        // now recipeList has data
        adapter.setDataset(recipeList);
        spinner.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRecipeClick(int positionOfSelectedRecipe) {
        positionForWidget = positionOfSelectedRecipe;
        Intent recipeSteps = new Intent(this, RecipeSteps.class);
        recipeSteps.putExtra(Constants.SELECTED_RECIPE, (Parcelable) recipeList.get(positionOfSelectedRecipe));
        UserPreference.setSharedPref(recipeList.get(positionOfSelectedRecipe), this);
        startActivity(recipeSteps);
        sendBroadcast();
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
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        UserPreference.setSharedPref(recipeList.get(positionForWidget), this);
        sendBroadcast();
    }

    private void sendBroadcast() {

        Intent intent = new Intent(this, IngredientsWidgets.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE\"");
        sendBroadcast(intent);
    }
}
