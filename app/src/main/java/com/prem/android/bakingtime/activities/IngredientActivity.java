package com.prem.android.bakingtime.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.prem.android.bakingtime.R;

import java.util.ArrayList;

import adapters.IngredientsAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import models.Ingredient;

public class IngredientActivity extends AppCompatActivity {

    private ArrayList<Ingredient> mIngredients;
    @BindView(R.id.ingredients_recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        ButterKnife.bind(this);

        android.support.v7.app.ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.ingredients_recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        IngredientsAdapter mAdapter = new IngredientsAdapter(this);

        if (savedInstanceState != null) {
            mIngredients = savedInstanceState.getParcelableArrayList("INGREDIENTS_AERIALIST");
            mAdapter.setIngredientsData(mIngredients);
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable("RECIPE_INGREDIENTS_BUNDLE");
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);


        } else {
            if (getIntent().getParcelableArrayListExtra("RECIPE_INGREDIENTS") != null) {
                mIngredients = getIntent().getParcelableArrayListExtra("RECIPE_INGREDIENTS");
                mAdapter.setIngredientsData(mIngredients);
            }
        }
        //attach recyclerView to adapter
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("INGREDIENTS_AERIALIST", mIngredients);
        //save the current recyclerView position
        outState.putParcelable("RECIPE_INGREDIENTS_BUNDLE", mRecyclerView.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }
}
