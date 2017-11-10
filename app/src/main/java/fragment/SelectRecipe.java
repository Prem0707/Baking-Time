package fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.prem.android.bakingtime.R;
import com.prem.android.bakingtime.activities.MainActivity;
import com.prem.android.bakingtime.activities.RecipeSteps;

import java.util.ArrayList;

import adapters.RecipeAdapter;
import interfaces.TaskCompleted;
import utils.AsyncTaskRecipe;
import utils.Constants;
import utils.NetworkUtils;

/**
 * A simple fragment which will contain the MainRecipe cards.
 */
public class SelectRecipe extends Fragment implements TaskCompleted, RecipeAdapter.RecyclerViewClickListener {

    Context mContext;
    private RecipeAdapter adapter;
    private ProgressBar spinner;
    ArrayList<models.Recipe> recipeList;


    // required Constructor
    public SelectRecipe() {
    }

    public void provideContext(MainActivity mainActivity) {
        this.mContext = mainActivity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_recipe, container, false);
        spinner = (ProgressBar) view.findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);

        if(savedInstanceState == null) {

            //Initialisation of AsyncTask to get raw data
            if (NetworkUtils.checkDeviceOnline(mContext)) {
                AsyncTaskRecipe asyncTaskRecipe = new AsyncTaskRecipe(this);
                asyncTaskRecipe.execute();
            } else {
                Toast.makeText(mContext, "Check Your Network Connection", Toast.LENGTH_LONG).show();
            }
        }else{
            recipeList = savedInstanceState.getParcelableArrayList(Constants.RECIPE_LIST);
        }

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recipe_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecipeAdapter(this);
        adapter.provideContext(getContext());
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    //Called when the Fragment is visible to the user
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.RECIPE_LIST, recipeList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onTaskCompleted(ArrayList<models.Recipe> mRecipe) {
        this.recipeList = mRecipe;
        // now recipeList has data
        adapter.setDataset(recipeList);
        spinner.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRecipeClick(int position) {
        goesToStepsToMake(position);
    }

    public void goesToStepsToMake(int position) {
        Intent recipeSteps = new Intent(mContext, RecipeSteps.class);
        recipeSteps.putExtra(Constants.SELECTED_RECIPE, (Parcelable) recipeList.get(position));
        startActivity(recipeSteps);
    }
}
