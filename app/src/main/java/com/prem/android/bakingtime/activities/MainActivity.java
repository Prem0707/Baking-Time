package com.prem.android.bakingtime.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import utils.AsyncTaskRecipe;
import utils.Constants;
import utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements TaskCompleted, RecipeAdapter.RecyclerViewClickListener {

    private RecipeAdapter adapter;
    private ProgressBar spinner;
    ArrayList<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);

        if (savedInstanceState != null) {
            recipeList = savedInstanceState.getParcelableArrayList(Constants.RECIPE_LIST);
        } else {
            boolean checkInternetConnection = NetworkUtils.checkDeviceOnline(this);
            if (checkInternetConnection) {
                AsyncTaskRecipe asyncTaskRecipe = new AsyncTaskRecipe(this);
                asyncTaskRecipe.execute();
            } else {
                Toast.makeText(this, "Check Your Network Connection", Toast.LENGTH_LONG).show();
            }
        }

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recipe_recyclerview);
        GridLayoutManager mGridLayoutManager = BasicUtility.gridLayoutManagerAccordingToOrientation(this);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        adapter = new RecipeAdapter(this, MainActivity.this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        spinner.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.RECIPE_LIST, recipeList);
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
        Intent recipeSteps = new Intent(this, RecipeSteps.class);
        recipeSteps.putExtra(Constants.SELECTED_RECIPE, (Parcelable) recipeList.get(positionOfSelectedRecipe));
        startActivity(recipeSteps);
    }
}
