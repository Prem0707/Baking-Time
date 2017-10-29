package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prem.android.bakingtime.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import interfaces.TaskCompleted;
import models.Recipe;

/**
 * A simple fragment which will contain the MainRecipe cards.
 */
public class SelectRecipe extends Fragment implements TaskCompleted{


    ArrayList<Recipe> recipeList;

    // required Constructor
    public SelectRecipe() { }

    @BindView(R.id.recipe_recyclerview) RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_recipe, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

     //Called when the Fragment is visible to the user
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onTaskCompleted(ArrayList<Recipe> mRecipe) {
        this.recipeList = mRecipe;
    }
}
