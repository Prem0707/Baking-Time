package com.prem.android.bakingtime.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.prem.android.bakingtime.R;

import java.util.ArrayList;

import adapters.IngredientsAdapter;
import models.Ingredient;

public class IngredientActivity extends AppCompatActivity {

    private ArrayList<Ingredient> mIngredients;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private IngredientsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        if(getIntent().getParcelableExtra("RECIPE_INGREDIENTS") != null){
            mIngredients = getIntent().getParcelableExtra("RECIPE_INGREDIENTS");
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recipe_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mAdapter = new IngredientsAdapter(this);

    }
}
